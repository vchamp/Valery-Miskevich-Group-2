var Currency;

$(document).ready(function () {
    console.log("ready");
    initElements();

    $('.currencyField').keyup(function () {
        doConvert($(this));
    });

    $('.currencySelect').change(function(){
        doConvert($(this));
    })
});

function initElements() {

    currencyList.forEach(function(element, index, list) {
        $('select').append('<option >' + element.name+'</option>');
    });

    initCurrencyInputs();

}

function initCurrencyInputs() {
    $(".currencyField").keydown(function (e) {
        // Allow: backspace, delete, tab, escape, enter and .
        if ($.inArray(e.keyCode, [46, 8, 9, 27, 13, 110, 190]) !== -1 ||
                // Allow: Ctrl+A
            (e.keyCode == 65 && e.ctrlKey === true) ||
                // Allow: home, end, left, right
            (e.keyCode >= 35 && e.keyCode <= 39)) {
            // let it happen, don't do anything
            return;
        }
        // Ensure that it is a number and stop the keypress
        if ((e.shiftKey || (e.keyCode < 48 || e.keyCode > 57)) && (e.keyCode < 96 || e.keyCode > 105)) {
            e.preventDefault();
        }
    });
}

function doConvert(element) {
    var isTop = element.hasClass('top');
    var typeFrom = isTop ? 'top' : 'bottom';
    var typeTo = !isTop ? 'top' : 'bottom';

    console.log(isTop);
    var fromCurr = currencyList[$('#select_'+typeFrom+' option:selected').index()];
    console.log(fromCurr);
    var toCurr = currencyList[$('#select_'+typeTo+' option:selected').index()];
    console.log(toCurr);
    var fromVal = $('#input_'+typeFrom).val();
    console.log(fromVal);
    var result = fromVal/fromCurr.ratio*toCurr.ratio;
    console.log(result);
    $('#input_'+typeTo).val(round(result));

}

function round(value){
    return parseFloat(Math.round(value * 100) / 100).toFixed(2);
}
Currency = function (name, ratio) {
    this.name = name;
    this.ratio = ratio;
};
var currencyList = [
    new Currency("EURO", 1),
    new Currency("GPB", 0.7895),
    new Currency("USD", 1.2654),
    new Currency("RUR", 52.9065),
    new Currency("BYR", 13565.7692)
];