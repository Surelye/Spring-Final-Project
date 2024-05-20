function calculateStockPurchasePrice() {
    const companyAndPrice = document.getElementById('company').value;
    const amount = document.getElementById('amount').value;
    const commission = document.getElementById('commission').value;

    if (companyAndPrice !== '' && amount !== '' && commission !== '') {
        const parsedPrice = parseFloat(companyAndPrice.substring(companyAndPrice
            .lastIndexOf('|') + 1))
        const parsedAmount = parseFloat(amount)
        const parsedCommission = parseFloat(commission)
        const result = parsedPrice * parsedAmount * (1 + parsedCommission / 100)

        document.getElementById('purchase-price').innerText =
            `Стоимость акций: ${result.toFixed(3)} рублей.`
        document.getElementById('purchase-price-holder').value = result.toFixed(3)
    } else {
        document.getElementById('purchase-price').innerText = 'Стоимость акций: '
    }
}

$(document).ready(function () {
    calculateStockPurchasePrice()
})