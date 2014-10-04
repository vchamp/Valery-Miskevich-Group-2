package com.epam.classloading;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.security.AccessController;
import java.security.CodeSource;
import java.security.PermissionCollection;
import java.security.Permissions;
import java.security.PrivilegedActionException;
import java.security.PrivilegedExceptionAction;
import java.security.SecureClassLoader;
import java.security.cert.Certificate;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import org.apache.log4j.Logger;


public class TestClassLoader extends SecureClassLoader {
	
	static final Logger logger = Logger.getLogger(TestClassLoader.class);

    private String JAR_PATH = "D:/eclipse/workspace2/JMP/lib/test_v2.jar";

    public TestClassLoader(ClassLoader parent) {
        super(parent);
    }

    public TestClassLoader() {
        this(TestClassLoader.class.getClassLoader());
    }

    public TestClassLoader(String jarPath) {
        this(TestClassLoader.class.getClassLoader());
        JAR_PATH = jarPath;
    }
    
    protected Class findClass(final String name) throws ClassNotFoundException {
    	
    	System.out.println("find class: "+name);
    	
        SecurityManager sm = System.getSecurityManager();
        // First check if we have permission to access the package.
        if (sm != null) {
            int i = name.lastIndexOf('.');
            if (i != -1) {
                sm.checkPackageDefinition(name.substring(0, i));
            }
        }
        // Now read in the bytes and define the class
        Class result = findLoadedClass(name);
        if (result != null) {
            logger.info("% Class " + name + " found in cache");
            return result;
        }
        
        
        try {  
            JarFile jar = new JarFile(JAR_PATH); 
            
            String name2 = name.replace('.','/') + ".class";
            
			JarEntry entry = jar.getJarEntry(name2);  
            InputStream is = jar.getInputStream(entry);  
            ByteArrayOutputStream byteStream = new ByteArrayOutputStream();  
            int nextValue = is.read();  
            while (-1 != nextValue) {  
                byteStream.write(nextValue);  
                nextValue = is.read();  
            }  
  
            byte[] classByte = byteStream.toByteArray();  
            result = defineClass(name, classByte, 0, classByte.length);  
              
            return result;  
        } catch (Exception e) {  
            logger.error("jar" , e);  
        }  
        
        
        final File f = findFile(name.replace('.', '/'), ".class");
        logger.info("% Class " + name + f == null ? "" : " found in " + f);
        if (f == null) {
            return findSystemClass(name);
        }
        try {
            return (Class)
                    AccessController.doPrivileged(new PrivilegedExceptionAction() {

                        @Override
                        public Object run() throws Exception {
                            byte[] classBytes = loadFileAsBytes(f);
                            CodeSource cs = getCodeSource(f);
                            return defineClass(name,
                                    classBytes, 0, classBytes.length, cs);
                        }
                    });


        } catch (ClassFormatError e) {
            throw new ClassNotFoundException(
                    "Format of class file incorrect for class "
                            + name + ": " + e);
        } catch (PrivilegedActionException e) {
            throw new ClassNotFoundException(
                    "Format of class file incorrect for class "
                            + name + ": " + e);
        }
    }

    protected CodeSource getCodeSource(File f) {
        Certificate[] cert = null;
        try {
            return new CodeSource(f.toURL(), cert);
        } catch (MalformedURLException mue) {
            logger.error(mue.getMessage());
            //mue.printStackTrace();
        }
        return null;
    }

    protected PermissionCollection getPermissions(CodeSource codesource) {
        PermissionCollection pc = new Permissions();
        pc.add(new RuntimePermission("createClassLoader"));
        pc.add(new RuntimePermission("exitVM"));
        return pc;
    }

    protected java.net.URL findResource(String name) {
        File f = findFile(name, "");
        if (f == null) return null;
        try {
            return f.toURL();
        } catch (java.net.MalformedURLException e) {
            return null;
        }
    }

    private File findFile(String name, String extension) {
        File f = new File((new File(JAR_PATH).getPath()
                + File.separatorChar + name.replace('/', File.separatorChar) + extension));
        if (f.exists()) return f;
        return null;
    }

    public static byte[] loadFileAsBytes(File file)
            throws IOException {
        byte[] result = new byte[(int) file.length()];
        FileInputStream f = new FileInputStream(file);
        try {
            f.read(result, 0, result.length);
        } finally {
            try {
                f.close();
            } catch (Exception e) {
                logger.error(e.getMessage());
            }
        }
        return result;
    }
}
