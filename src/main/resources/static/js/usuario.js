//datatables - lista de médicos
$(document).ready(function() {
	moment.locale('pt-BR');
	var table = $('#table-usuarios').DataTable({
		searching : true,
		lengthMenu : [ 5, 10 ],
		processing : true,
		serverSide : true,
		responsive : true,
		ajax : {
			url : '/usuarios/datatables/server/usuarios',
			data : 'data'
		},
		columns : [
				{data : 'id'},
				{data : 'email'},
				{data: 'usuSenha'},
				{	data : 'ativo', 
					render : function(ativo) {
						return ativo == true ? 'Sim' : 'Não';
					}
				},
				{	data : 'setor.setNome'},
				{	data : 'id',	
					render : function(id) {
						return ''.concat('<a class="btn btn-success btn-sm btn-block"', ' ')
								 .concat('href="').concat('/usuarios/editar/').concat(id, '"', ' ') 
								 .concat('role="button" title="Editar" data-toggle="tooltip" data-placement="right">', ' ')
								 .concat('<i class="fas fa-edit"></i></a>');
					},
					orderable : false
				},
				{	data : 'id',	
					render : function(id) {
						return ''.concat('<a class="btn btn-info btn-sm btn-block"', ' ') 
								 .concat('id="dp_').concat(id).concat('"', ' ') 
								 .concat('role="button" title="Editar" data-toggle="tooltip" data-placement="right">', ' ')
								 .concat('<i class="fas fa-edit"></i></a>');
					},
					orderable : false
				}
		]
	});
});	

$('.pass').keyup(function(){
	$('#senha1').val() === $('#senha2').val()
	    ? $('#senha3').removeAttr('readonly')
	    : $('#senha3').attr('readonly', 'readonly');
});








