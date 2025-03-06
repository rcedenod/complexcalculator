console.log('cargando');

document.getElementById('input-format').addEventListener('change', toggleInputFormat);
document.getElementById('logout-button').addEventListener('click', logout);
document.getElementById('calculate-button').addEventListener('click', calculate);

function toggleInputFormat() {

    console.log("toggleinputformat");

    const inputFormat = document.getElementById('input-format').value;
    const rectangularInputs = document.getElementById('rectangular-inputs');
    const polarInputs = document.getElementById('polar-inputs');

    if (inputFormat === 'rectangular') {
        rectangularInputs.style.display = 'block';
        polarInputs.style.display = 'none';
    } else {
        rectangularInputs.style.display = 'none';
        polarInputs.style.display = 'block';
    }
    clearForm();
}

function calculate() {

    console.log("ejecute calculate");

    const inputFormat = document.getElementById('input-format').value;
    const operation = document.getElementById('operation').value;
    const outputFormat = document.getElementById('output-format').value;

    let params = new URLSearchParams();
    params.append("tipoOperacion", operation);
    params.append("inputFormat", inputFormat);
    params.append("outputFormat", outputFormat);

    function isValidNumber(value) {
        value = value.replace(',', '.');
        return !isNaN(value) && value.trim() !== ''  && value !== null;
    }

    if (inputFormat === 'rectangular') {
        const real1 = document.getElementById('real1').value;
        const imag1 = document.getElementById('imag1').value;
        const real2 = document.getElementById('real2').value;
        const imag2 = document.getElementById('imag2').value;

        if (!isValidNumber(real1) || !isValidNumber(imag1) || !isValidNumber(real2) || !isValidNumber(imag2)) {
            document.getElementById('result').innerText = 'Por favor, ingresa valores válidos.';
            return;
        }

        params.append("valor1", real1);
        params.append("valor2", imag1);
        params.append("valor3", real2);
        params.append("valor4", imag2);

    } else if (inputFormat === 'polar') {
        const r1 = document.getElementById('r1').value;
        const theta1 = document.getElementById('theta1').value;
        const r2 = document.getElementById('r2').value;
        const theta2 = document.getElementById('theta2').value;

        if (!isValidNumber(r1) || !isValidNumber(theta1) || !isValidNumber(r2) || !isValidNumber(theta2)) {
            document.getElementById('result').innerText = 'Por favor, ingresa valores válidos.';
            return;
        }

        params.append("valor1", r1);
        params.append("valor2", theta1);
        params.append("valor3", r2);
        params.append("valor4", theta2);
    }

    fetch(`http://localhost:8080/api/complex/operacion?${params.toString()}`, {
       method: 'GET',
        headers: {
            "Content-Type": "application/x-www-form-urlencoded"
        }
    })
    .then(response => response.text())
    .then(data => {
        if (data == 'No tiene sesion') {
            window.location.href = "login";
        }

        console.log(data);
        displayResult(data, outputFormat);
    })
    .catch(error => {
        document.getElementById('result').innerText = 'Error al realizar el cálculo.';
        console.error(error);
    });
}

function logout() {
    console.log("aprete logout");

    fetch('http://localhost:8080/api/auth/logout', {
        method: 'POST',
        headers: {
            "Content-Type": "application/x-www-form-urlencoded"
        }
    })
    .then(response => response.text())
    .then(data => {
        if (data === "Sesion cerrada") {
            console.log("listo");
            window.location.href = "/"; // Redirige a la página de login
        }
    })
    .catch(error => {
        console.error('Error:', error);
    });
}


function displayResult(data) {
    const resultElement = document.getElementById('result');
    resultElement.innerText = data;
}

function clearForm() {
    document.querySelectorAll('#calculator-form input').forEach(input => input.value = '');
}

