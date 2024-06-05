function calculateSellProfit() {
    let displayProfit = false
    const commission = parseFloat(document.getElementById("commission").getAttribute('value'))
    const stockPriceRegex = /^stockPrice\d+$/
    const amountRegex = /^amount\d+$/
    const allElements = document.querySelectorAll("*")
    const stockElements = Array.prototype.filter.call(allElements, function(element) {
        return stockPriceRegex.test(element.id);
    });
    const amountElements = Array.prototype.filter.call(allElements, function(element) {
        return amountRegex.test(element.id);
    });
    let sellProfit = 0

    for (let i = 0; i < stockElements.length; ++i) {
        console.log(stockElements[i])
        let amount = parseFloat(amountElements[i].value)
        if (isNaN(amount)) {
            continue
        }
        displayProfit = true
        let price = parseFloat(stockElements[i].getAttribute("value"))
        sellProfit += amount * price
    }

    if (displayProfit) {
        sellProfit = (1 - (commission / 100)) * sellProfit
        document.getElementById("sell-profit").innerText =
            `Ваша выручка: ${sellProfit.toFixed(3)} рублей.`
    } else {
        document.getElementById('sell-profit').innerText = 'Ваша выручка: '
    }
}

$(document).ready(function () {
    calculateSellProfit()
})