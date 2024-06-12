function checkEnoughBalance() {
    const purchasePrice = parseFloat(document.getElementById('purchase-price-holder').value);
    const balance = parseFloat(document.getElementById('balance-holder').value);
    const textElement = document.getElementById('message');

    if (balance < purchasePrice) {
        textElement.innerText = 'На Вашем балансе недостаточно средств для совершения операции.';
        textElement.classList.remove('hidden');
        textElement.classList.add('show');
    } else {
        textElement.innerText = '';
        textElement.classList.remove('show');
        textElement.classList.add('hidden');
    }
}

document.addEventListener('DOMContentLoaded', function () {
    checkEnoughBalance();
});