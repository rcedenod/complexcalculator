document.getElementById('input-format').addEventListener('change', toggleInputFormat);
document.getElementById('output-format').addEventListener('change', toggleOutputFormat);

// Función para actualizar el formulario al cambiar el formato de entrada
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

// Función que se activa al cambiar el formato de salida
function toggleOutputFormat() {
    // Puedes agregar lógica si deseas cambiar el formato de salida
}

// Asegurarse de que el formulario se actualice correctamente al cargar la página
document.addEventListener("DOMContentLoaded", function() {
    toggleInputFormat();
});

// Función para calcular
function calculate() {
    const inputFormat = document.getElementById('input-format').value;
    const operation = document.getElementById('operation').value;
    const outputFormat = document.getElementById('output-format').value;

    let url = '';
    let params = '';

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
            document.getElementById('result').innerText = 'Por favor, ingresa valores válidos para todos los campos.';
            return;
        }

        params = `?real1=${real1}&imag1=${imag1}&real2=${real2}&imag2=${imag2}`;

        switch (operation) {
            case 'suma':
                url = '/api/complex/suma-rect';
                break;
            case 'resta':
                url = '/api/complex/resta-rect';
                break;
            case 'multiplicacion':
                url = '/api/complex/multiplicacion-rect';
                break;
            case 'division':
                url = '/api/complex/division-rect';
                break;
            default:
                return;
        }

    } else if (inputFormat === 'polar') {
        const r1 = document.getElementById('r1').value;
        const theta1 = document.getElementById('theta1').value;
        const r2 = document.getElementById('r2').value;
        const theta2 = document.getElementById('theta2').value;

        if (!isValidNumber(r1) || !isValidNumber(theta1) || !isValidNumber(r2) || !isValidNumber(theta2)) {
            document.getElementById('result').innerText = 'Por favor, ingresa valores válidos para todos los campos.';
            return;
        }

        const real1 = r1 * Math.cos(theta1);
        const imag1 = r1 * Math.sin(theta1);
        const real2 = r2 * Math.cos(theta2);
        const imag2 = r2 * Math.sin(theta2);

        params = `?real1=${real1}&imag1=${imag1}&real2=${real2}&imag2=${imag2}`;

        switch (operation) {
            case 'suma':
                url = '/api/complex/suma-polar';
                break;
            case 'resta':
                url = '/api/complex/resta-polar';
                break;
            case 'multiplicacion':
                url = '/api/complex/multiplicacion-polar';
                break;
            case 'division':
                url = '/api/complex/division-polar';
                break;
            default:
                return;
        }

    }

    console.log(url + params);

    fetch(url + params)
        .then(response => response.json())
        .then(data => {
            displayResult(data, outputFormat);
        })
        .catch(error => {
            document.getElementById('result').innerText = 'Error al realizar el cálculo.';
            console.error(error);
        });
}

// Función para mostrar el resultado
function displayResult(data, outputFormat) {
    const resultElement = document.getElementById('result');

    if (outputFormat === 'rectangular') {
        resultElement.innerText = `Resultado: ${data.real.toFixed(2)} + ${data.imaginary.toFixed(2)}i`;
    } else if (outputFormat === 'polar') {
        const r = data.r.toFixed(2);
        const theta = data.theta.toFixed(2);
        resultElement.innerText = `Resultado: Magnitud = ${r}, Ángulo = ${theta} radianes`;
    }
}


// Función para limpiar el formulario
function clearForm() {
    // Limpiar los campos de entrada
    const inputs = document.querySelectorAll('#calculator-form input');
    inputs.forEach(input => input.value = '');
}
