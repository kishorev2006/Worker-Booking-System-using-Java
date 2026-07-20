function loadWorkers(serviceId) {
    if (!serviceId) {
        document.getElementById('workersContainer').innerHTML = '';
        return;
    }
    fetch('/user/workers?serviceId=' + serviceId)
        .then(response => response.text())
        .then(html => {
            document.getElementById('workersContainer').innerHTML = html;
        });
}
