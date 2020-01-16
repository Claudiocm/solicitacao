$('document').ready(function() {
	$.ajax({
		type : "POST",
		url : "/chamados/listar",
		dataType : "json",
		success : function(data) {
			var situacao = [];
			var chamado = [];
			for (var i = 0; i < data.length; i++) {
				situacao.push(data[i].situacao.descricao);
				chamado.push(data[i].chamado.id);
			}
			grafico(situacao, chamado);
		}
	});
})

function grafico(situacao, chamado) {
	var chart = document.getElementById('grfTotal').getContext('2d');
	
	var chart = new Chart(ctx, {
		type: 'pieChart',
		data: {
			labels: situacao,
			datasets:[{
				label: '´Painel de chamados',
				data: chamado
			}]
		}
	})

}