/**
 * Datatable histórico de consultas
*/
$(document).ready(function() {
    moment.locale('pt-BR');
    var table = $('#table-solicitante-historico').DataTable({
        searching : false,
        lengthMenu : [ 5, 10 ],
        processing : true,
        serverSide : true,
        responsive : true,
        order: [2, 'desc'],
        ajax : {
            url : '/chamados/datatables/server/historico-solicitante',
            data : 'data'
        },
        columns : [
            {data : 'id'},
            {data : 'solicitante.nome'},
            {data : 'dataAberttura', render:
                function( dataConsulta ) {
                    return moment(dataConsulta).format('LLL');
                }
            },
            {data : 'tecnico.tecNome'},
            {data : 'servico.serNome'},
            {orderable : false,	data : 'id', "render" : function(id) {
                    return '<a class="btn btn-success btn-sm btn-block" href="/chamados/editar/'
                            + id + '" role="button"><i class="fas fa-edit"></i></a>';
                }
            },
            {orderable : false,	data : 'id', "render" : function(id) {
                    return '<a class="btn btn-danger btn-sm btn-block" href="/chamados/excluir/'
                    + id +'" role="button" data-toggle="modal" data-target="#confirm-modal"><i class="fas fa-times-circle"></i></a>';
                }
            }
        ]
    });
});
/**
 * Datatable histórico de consultas
*/
$(document).ready(function() {
    moment.locale('pt-BR');
    var table = $('#table-paciente-historico').DataTable({
        searching : false,
        lengthMenu : [ 5, 10 ],
        processing : true,
        serverSide : true,
        responsive : true,
        order: [2, 'desc'],
        ajax : {
            url : '/agendamentos/datatables/server/historico',
            data : 'data'
        },
        columns : [
            {data : 'id'},
            {data : 'paciente.nome'},
            {data: 'dataConsulta', render:
                function( dataConsulta ) {
                    return moment(dataConsulta).format('LLL');
                }
            },
            {data : 'medico.nome'},
            {data : 'especialidade.titulo'},
            {orderable : false,	data : 'id', "render" : function(id) {
                    return '<a class="btn btn-success btn-sm btn-block" href="/agendamentos/editar/consulta/'
                            + id + '" role="button"><i class="fas fa-edit"></i></a>';
                }
            },
            {orderable : false,	data : 'id', "render" : function(id) {
                    return '<a class="btn btn-danger btn-sm btn-block" href="/agendamentos/excluir/consulta/'
                    + id +'" role="button" data-toggle="modal" data-target="#confirm-modal"><i class="fas fa-times-circle"></i></a>';
                }
            }
        ]
    });
});