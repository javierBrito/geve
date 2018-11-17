function mostrarConfirmacion(mensaje) {
	return confirm(mensaje);
}

function mayusculas(obj) {
	obj.value = obj.value.toUpperCase();
}

function abrirURL(url, target, width, height) {
	var left = (screen.width / 2)-(width / 2);
	var top = (screen.height / 2)-(height / 2);
	var opciones = "width=" + width + ",height=" + height + ", top=" + top + ", left=" + left;
	window.open(url, target, opciones);
}

function soloNumeros(evt) {
	var charCode = (evt.which) ? evt.which : event.keyCode;
    if (charCode > 31 && (charCode < 48 || charCode > 57))
       return false;
    return true;	
}

function soloLetras(evt) {
    evt = (evt) ? evt : event;
    var charCode = (evt.charCode) ? evt.charCode : ((evt.keyCode) ? evt.keyCode : ((evt.which) ? evt.which : 0));
    if (charCode > 31 && (charCode < 65 || charCode > 90) && (charCode < 97 || charCode > 122) && charCode != 32) {
       return false;
    }
    return true;
}

function validarInicial(obj, txt) {
	var text = obj.value; 
	if(text.length < txt.length) {
		return;
	}
	if(text.substring(0, txt.length) == txt) {
		return;
	} else {
		obj.value = txt + obj.value;
		return;
	}
}
//<![CDATA[

function copiarValor(origenElement, destinoElement) {
	var origen = document.getElementById(origenElement);
	var destino = document.getElementById(destinoElement);
	destino.value = origen.value;
}

function validarNumeros(evt) {
	var e = evt || window.event;
	var key = e.keyCode || e.which;

	if (!e.shiftKey && !e.altKey && !e.ctrlKey && key >= 48 && key <= 57
			|| key >= 96 && key <= 105 || key == 8 || key == 9 || key == 13
			|| key == 35 || key == 36 || key == 37 || key == 39 || key == 46
			|| key == 45) {
		e.returnValue = true;
	} else {
		e.returnValue = false;
		if (e.preventDefault)
			e.preventDefault();
	}
}
// ]]>
