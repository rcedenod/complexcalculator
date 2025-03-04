document.getElementById('input-format').addEventListener('change', toggleInputFormat);

function toggleInputFormat() {
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

document.addEventListener("DOMContentLoaded", function() {
    toggleInputFormat();
});

function calculate() {
    const inputFormat = document.getElementById('input-format').value;
    const operation = document.getElementById('operation').value;
    const outputFormat = document.getElementById('output-format').value;

    let url = '/api/complex/operacion';
    let params = new URLSearchParams();
    params.append("tipoOperacion", operation);
    params.append("inputFormat", inputFormat);
    params.append("outputFormat", outputFormat);

    function isValidNumber(value) {
        value = value.replace(',', '.');
        return !isNaN(value) && value.trim() !== '';
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

    fetch(`${url}?${params.toString()}`)
        .then(response => response.json())
        .then(data => {
            displayResult(data, outputFormat);
        })
        .catch(error => {
            document.getElementById('result').innerText = 'Error al realizar el cálculo.';
            console.error(error);
        });
}

function displayResult(data, outputFormat) {
    const resultElement = document.getElementById('result');

    if (outputFormat === 'rectangular') {
        // Mostramos en formato rectangular (a + bi)
        resultElement.innerText = `Resultado: ${data.real.toFixed(2)} + ${data.imaginary.toFixed(2)}i`;
    } else if (outputFormat === 'polar') {
        // Mostramos en formato polar (r(cosθ + i*sinθ))
        resultElement.innerText = `Resultado: ${data.r.toFixed(2)}(cos(${data.theta.toFixed(2)}) + i*sin(${data.theta.toFixed(2)}))`;
    }
}

function clearForm() {
    document.querySelectorAll('#calculator-form input').forEach(input => input.value = '');
}
