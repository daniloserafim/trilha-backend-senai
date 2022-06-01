function validaFaleConosco() {
	if (document.frmfaleconosco.txtnome.value==""){
		alert("Preencha o campo Nome");
		document.frmfaleconosco.txtnome.focus();
		return false;
	}
	return true;
}

function verificaMotivo(motivo) {
	var elemento = document.getElementById("opcaoProduto");
	
	if (motivo == "PR") {
		var select = document.createElement("select");
		select.setAttribute("name", "selProduto");
		
		var option = document.createElement("option");
		option.setAttribute("value", "");
		var texto = document.createTextNode("Escolha");
		option.appendChild(texto);
		select.appendChild(option);
		
		var option = document.createElement("option");
		option.setAttribute("value", "FR");
		var texto = document.createTextNode("Freezer");
		option.appendChild(texto);
		select.appendChild(option);
		
		var option = document.createElement("option");
		option.setAttribute("value", "GE");
		var texto = document.createTextNode("Geladeira");
		option.appendChild(texto);
		select.appendChild(option);
		
		elemento.appendChild(select);
	} else {
		if (elemento.firstChild) 
			elemento.removeChild(elemento.firstChild);
	}
}

$(document).ready(function() {
	$("header").load("/ProjetoTrilhaWeb/pages/site/general/cabecalho.html");
	$("nav").load("/ProjetoTrilhaWeb/pages/site/general/menu.html");
	$("footer").load("/ProjetoTrilhaWeb/pages/site/general/rodape.html");
});