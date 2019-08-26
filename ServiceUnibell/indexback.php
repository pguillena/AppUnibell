<?php
require 'Slim/Slim.php';

require 'bl/bluser.php';
require 'bl/blbienes.php';
require 'bl/blfunction.php';
require 'bl/blcore.php';
require 'bl/blevents.php';
require 'bl/blclients.php';
require 'bl/blactivity.php';
require 'bl/bltypeactivity.php';
require 'bl/blprospects.php';
require 'bl/blprograms.php';
require 'bl/blproducts.php';
require 'bl/blsales.php';
require 'bl/blseller.php';
require 'bl/blagent.php';
require 'bl/bldashboard.php';
require 'bl/blquotation.php';
require 'bl/blbinnable.php';
require 'bl/bltablas.php';
require 'bl/blagenciatra.php';
require 'bl/blorder.php'; 
require 'bl/blorderdetail.php';
require 'bl/blVendedores.php';
require 'bl/blinventario.php';
require 'bl/blmotivosrechazo.php';
require 'bl/blcdptablas.php';
require 'bl/blcdpordenes.php';


\Slim\Slim::registerAutoloader();

$app = new \Slim\Slim();

// GET route
$app->get(
    '/',
    function () {
        $template = <<<EOT
<!DOCTYPE html>
    <html>
        <head>
            <meta charset="utf-8"/>
            <title>Slim Framework for PHP 5</title>
            <style>
                html,body,div,span,object,iframe,
                h1,h2,h3,h4,h5,h6,p,blockquote,pre,
                abbr,address,cite,code,
                del,dfn,em,img,ins,kbd,q,samp,
                small,strong,sub,sup,var,
                b,i,
                dl,dt,dd,ol,ul,li,
                fieldset,form,label,legend,
                table,caption,tbody,tfoot,thead,tr,th,td,
                article,aside,canvas,details,figcaption,figure,
                footer,header,hgroup,menu,nav,section,summary,
                time,mark,audio,video{margin:0;padding:0;border:0;outline:0;font-size:100%;vertical-align:baseline;background:transparent;}
                body{line-height:1;}
                article,aside,details,figcaption,figure,
                footer,header,hgroup,menu,nav,section{display:block;}
                nav ul{list-style:none;}
                blockquote,q{quotes:none;}
                blockquote:before,blockquote:after,
                q:before,q:after{content:'';content:none;}
                a{margin:0;padding:0;font-size:100%;vertical-align:baseline;background:transparent;}
                ins{background-color:#ff9;color:#000;text-decoration:none;}
                mark{background-color:#ff9;color:#000;font-style:italic;font-weight:bold;}
                del{text-decoration:line-through;}
                abbr[title],dfn[title]{border-bottom:1px dotted;cursor:help;}
                table{border-collapse:collapse;border-spacing:0;}
                hr{display:block;height:1px;border:0;border-top:1px solid #cccccc;margin:1em 0;padding:0;}
                input,select{vertical-align:middle;}
                html{ background: #EDEDED; height: 100%; }
                body{background:#FFF;margin:0 auto;min-height:100%;padding:0 30px;width:440px;color:#666;font:14px/23px Arial,Verdana,sans-serif;}
                h1,h2,h3,p,ul,ol,form,section{margin:0 0 20px 0;}
                h1{color:#333;font-size:20px;}
                h2,h3{color:#333;font-size:14px;}
                h3{margin:0;font-size:12px;font-weight:bold;}
                ul,ol{list-style-position:inside;color:#999;}
                ul{list-style-type:square;}
                code,kbd{background:#EEE;border:1px solid #DDD;border:1px solid #DDD;border-radius:4px;-moz-border-radius:4px;-webkit-border-radius:4px;padding:0 4px;color:#666;font-size:12px;}
                pre{background:#EEE;border:1px solid #DDD;border-radius:4px;-moz-border-radius:4px;-webkit-border-radius:4px;padding:5px 10px;color:#666;font-size:12px;}
                pre code{background:transparent;border:none;padding:0;}
                a{color:#70a23e;}
                header{padding: 30px 0;text-align:center;}
            </style>
        </head>
        <body>
            <header>
                <a href="http://www.slimframework.com"><img src="data:image/png;base64,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" alt="Slim"/></a>
            </header>
            <h1>Welcome to Slim!</h1>
            <p>
                Congratulations! Your Slim application is running. If this is
                your first time using Slim, start with this <a href="http://www.scp.com.pe/SkyCore/crm.apk" target="_blank">"DESCARGA APPCRM"</a>.
            </p>
            <section>
                <h2>Get Started</h2>
                <ol>
                    <li>The application code is in <code>index.php</code></li>
                    <li>Read the <a href="http://docs.slimframework.com/" target="_blank">online documentation</a></li>
                    <li>Follow <a href="http://www.twitter.com/slimphp" target="_blank">@slimphp</a> on Twitter</li>
                </ol>
            </section>
            <section>
                <h2>Slim Framework Community</h2>

                <h3>Support Forum and Knowledge Base</h3>
                <p>
                    Visit the <a href="http://help.slimframework.com" target="_blank">Slim support forum and knowledge base</a>
                    to read announcements, chat with fellow Slim users, ask questions, help others, or show off your cool
                    Slim Framework apps.
                </p>

                <h3>Twitter</h3>
                <p>
                    Follow <a href="http://www.twitter.com/slimphp" target="_blank">@slimphp</a> on Twitter to receive the very latest news
                    and updates about the framework.
                </p>
            </section>
            <section style="padding-bottom: 20px">
                <h2>Slim Framework Extras</h2>
                <p>
                    Custom View classes for Smarty, Twig, Mustache, and other template
                    frameworks are available online in a separate repository.
                </p>
                <p><a href="https://github.com/codeguy/Slim-Extras" target="_blank">Browse the Extras Repository</a></p>
            </section>
        </body>
    </html>
EOT;
        echo $template;
    }
);
$app->post('/LoginOracle', 'bluser:LogUsuarioOracle'); // { "USU_DESCRI": "MORELLANA", "USU_PASSWD": "123" }

$app->post('/User', 'bluser:LogUsuarioOracleByImei');  // { "USU_DESCRI": "ATORRES", "USU_PASSWD": "*" ,"PHO_NROVER":"1", "PHO_NROIMEI":"9"}
$app->get('/User/:codigo', 'bluser:SelectUserByCodigo');
$app->get('/User/:codusu/:codrol', 'bluser:SelectRol');

$app->get('/Prospects/:codusu', 'blprospects:SelectAllProspectsByCodUsu');
$app->post('/Prospects', 'blprospects:InsertProspects');
$app->put('/Prospects', 'blprospects:UpdateProspects');
$app->delete('/Prospects/:codigo', 'blprospects:DeleteProspects');

//CONSULTAS
$app->get('/Query/User/Rol/:codusu', 'bluser:SelectRolByCodUsu');


//DASHBOARD
$app->get('/Dashboard/Events/:codeqp', 'blevents:SelectEventsByCodEqp');
$app->get('/Dashboard/Programs', 'bldashboard:SelectProgramsByMonth');
$app->get('/Dashboard/Prospects', 'bldashboard:SelectProspectsByYear');
$app->get('/Dashboard/Spy/Coordinates/:codest', 'bldashboard:SelectSpyGPSByGpsEst');

$app->get('/Events/Photos/:codusu', 'blevents:SelectPhotosByCodUsu');
$app->get('/Events/Photos/View/:codeve', 'blevents:SelectPhotosByCodEve');
$app->get('/Events/:codven/:fecreg', 'blevents:SelectEventsByFecReg');
$app->get('/Events/:codven', 'blevents:SelectAllEvents');

$app->put('/Events', 'blevents:UpdateEvents');
$app->post('/Events', 'blevents:InsertEvents');
$app->post('/Events/Photos', 'blevents:InsertPhotos');
$app->post('/Events/Upload', 'blevents:UploadFile');

$app->get('/Clients/:codven', 'blclients:SelectAllClients');
$app->get('/Clients/Address/:codcli', 'blclients:SelectAllAddress');
$app->get('/Clients/Address/Sync/:codven', 'blclients:SelectSyncAddress');
$app->get('/Clients/Contacts/:codcli', 'blclients:SelectAllContacts');
$app->post('/Clients/Contacts', 'blclients:InsertContact');
$app->get('/Clients/Contacts/Sync/:codven', 'blclients:SelectSyncContacts');
$app->get('/Clients/Resume/:codcli', 'blclients:SelectResumeClient');
$app->get('/Clients/Accounts/:codcli', 'blclients:SelectAccountsClient');
$app->get('/Clients/Vendedor/:prm', 'blclients:SelectClientesVendedor');

$app->get('/TypeActivity', 'blTypeActivity:SelectAllTypeActivity');
$app->post('/TypeActivity', 'blTypeActivity:InsertTypeActivity');
$app->put('/TypeActivity', 'blTypeActivity:UpdateTypeActivity');

$app->get('/Activity', 'blActivity:SelectAllActivity');
$app->post('/Activity', 'blActivity:InsertActivity');
$app->put('/Activity', 'blActivity:UpdateActivity');

$app->get('/Programs/:codven', 'blprograms:SelectAllPrograms');
$app->post('/Programs', 'blprograms:InsertPrograms');
$app->put('/Programs', 'blprograms:UpdatePrograms');

$app->get('/Products/:codven', 'blproducts:SelectAllProducts');
$app->post('/Products/Prices', 'blproducts:SelectProductsPricesByCodigo');
$app->get('/Products/Sales/:codart/:codcli/:codven', 'blproducts:SelectProductsSalesByCodigo');
$app->get('/Products/Stock/:codusu/:codigo', 'blproducts:SelectProductsByCodigo');
$app->get('/Products/Money/:codigo', 'blproducts:SelectMonedaArticulo');
$app->get('/Products/Alternativos/:codart/:codcli', 'blproducts:SELECTProducto_Alternativo');

$app->post('/Sales', 'blsales:SelectSalesByCodUsu');
$app->post('/Sales/Details', 'blsales:SelectSalesDetByCodUsu');

$app->get('/Seller/:codequ', 'blseller:SelectByCodEquSeller');

$app->get('/Agent/Coordinates/:codusu', 'blagent:SelectByCodUsuCoordinates');
$app->get('/Agent/Coordinates/:codusu/:fecreg', 'blagent:SelectByFecRegCoordinates');
$app->post('/Agent/Coordinates', 'blagent:InsertCoordinates'); //OLD
$app->post('/Agent/Spy', 'blagent:InsertSpyMetaData');

$app->get('/Settings/:codsuc', 'blagent:SelectByCodCiaSettings');

$app->get('/Version', 'blagent:SelectVersion');
$app->get('/Bienes', 'blbienes:SelectAllBienes');

$app->get('/Quotation/:codusu', 'blquotation:SelectQuotationByCodUsu');
$app->get('/Quotation/Details/:correl', 'blquotation:SelectQuotationByCorrel');
$app->get('/Quotation/Sync/:codusu', 'blquotation:SelectQuotationSync');
$app->get('/Quotation/Printer/:codmd5', 'blquotation:SelectQuotationByCodMD5');

//COTIZACION
$app->post('/Quotation', 'blquotation:InsertQuotation');
$app->post('/Quotation/Prices', 'blquotation:SelectPricesByCodCli');
$app->post('/Quotation/Mailing', 'blquotation:SendQuotationByCorrel');
$app->post('/Quotation/Demo', 'blquotation:SelectDemo');

//PEDIDO
$app->get('/Tablas', 'bltablas:SelectAllTablas');
$app->get('/Tablas/:Flag/:codcli', 'bltablas:SelectCondicionVenta');
$app->get('/Tablas/:Flag/:convta/:prm', 'bltablas:SelectFormaPago');
$app->get('/TablasAlmacenes/:codart/:codusu', 'bltablas:SelectAlmacenesByUsuario');

$app->get('/Agencias', 'blagenciatra:SelectAllAgenciaTra');

//BITACORA
$app->get('/Binnable/:nrodoc', 'blbinnable:SelectBinnableByNroDoc');

//PEDIDO
$app->get('/Pedido/:codusu', 'blorder:SelectOrderByCodUsu');
$app->get('/Pedido/Resume/:codped', 'blorder:SelectOrderResumen');

$app->get('/Pedido/Aprobacion/:codusu/:codest', 'blorder:SelectOrderAprobacion');


$app->get('/Pedido/Detail/:nro', 'blorderdetail:SelectPedidoDetailByNro');
$app->post('/Pedido/Validar', 'blorder:ValidacionPedido');

$app->post('/Pedido/Insert', 'blorder:InsertPedido');
$app->post('/Pedido/Update', 'blorder:UpdatePedido');
$app->post('/Pedido/Confirmar', 'blorder:ConfirmaPedido');
 
$app->post('/Pedido/Aprobar', 'blorder:PedidoAprobacionGerencia');
$app->post('/Pedido/Rechazar', 'blorder:PedidoRechazarGerencia');
$app->get('/Pedido/Copiar/:codped/:codUsu', 'blorder:Copiar_Pedido');


$app->post('/Inventario/InventarioGet', 'blinventario:InventarioGet');


$app->post('/Pedido/Cobro', 'blorder:PedidoCobro');
$app->post('/PedidoCobro/Update', 'blorder:PedidoCobroUpdate');
$app->post('/Pedido/FacturaAdelantada', 'blorder:PedidoFacturaAdelantada');


//$app->post('/PedidoDetail', 'blorder:InsertPedidoDetalle');
$app->post('/Pedido/Detail/Delete', 'blorderdetail:DeletePedidoDetLinea');
$app->post('/Pedido/Detail/Update', 'blorderdetail:UpdatePedidoDetalle');
$app->get('/Vendedores', 'blVendedores:SelectVendedores');

$app->get('/MotivosRechazo/:nropedido', 'blmotivosrechazo:SelectAllMotivosRechazo');

//SISTEMA DE CONTROL DE PRPODUCCIÓN MOVIL

//Tabla de Tablas
$app->get('/cdpTablas/:ptabla', 'blptablas:SelectAllTablas');
//Lista de Ordenes
$app->get('/cdpOrdenMaquinas/:ptipo,:numero', 'blcdpordenes:SelectMaquinasByOrden');
$app->get('/cdpOrdenDetalle/:ptipo,:numero', 'blcdpordenes:SelectDetalleByOrden');

$app->notFound(function(){
        $template = <<<EOT
<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8"/>
        <title>Slim Framework for PHP 5</title>
        <style>
            html,body,
            h1{margin:0;padding:0;border:0;outline:0;font-size:100%;vertical-align:baseline;background:transparent;}
            body{line-height:1;}               
            html{ background: #EDEDED; height: 100%; }
            body{background:#FFF;margin:0 auto;min-height:100%;padding:0 30px;width:440px;color:#666;font:14px/23px Arial,Verdana,sans-serif;}
            h1{margin:0 0 20px 0;}
            h1{color:#333;font-size:20px;}               
            header{padding: 30px 0;text-align:center;}
        </style>
    </head>
    <body>
        <header>
            <a href="http://www.slimframework.com"><img src="data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAHIAAAA6CAYAAABs1g18AAAAGXRFWHRTb2Z0d2FyZQBBZG9iZSBJbWFnZVJlYWR5ccllPAAABRhJREFUeNrsXY+VsjAMR98twAo6Ao4gI+gIOIKOgCPICDoCjCAjXFdgha+5C3dcv/QfFB5i8h5PD21Bfk3yS9L2VpGnlGW5kS9wJMTHNRxpmjYRy6SycgRvL18OeMQOTYQ8HvIoJKiiz43hgHkq1zvK/h6e/TyJQXeV/VyWBOSHA4C5RvtMAiCc4ZB9FPjgRI8+YuKcrySO515a1hoAY3nc4G2AH52BZsn+MjaAEwIJICKAIR889HljMCcyrR0QE4v/q/BVBQva7Q1tAczG18+x+PvIswHEAslLbfGrMZKiXEOMAMy6LwlisQCJLPFMfKdBtli5dIihRyH7A627Iaiq5sJ1ThP9xoIgSdWSNVIHYmrTQgOgRyRNqm/M5PnrFFopr3F6B41cd8whRUSufUBU5EL4U93AYRnIWimCIiSI1wAaAZpJ9bPnxx8eyI3Gt4QybwWa6T/BvbQECUMQFkhd3jSkPFgrxwcynuBaNT/u6eJIlbGOBWSNIUDFEIwPZFAtBfYrfeIOSRSXuUYCsprCXwUIZWYnmEhJFMIocMDWjn206c2EsGLCJd42aWSyBNMnHxLEq7niMrY2qyDbQUbqrrTbwUPtxN1ZZCitQV4ZSd6DyoxhmRD6OFjuRUS/KdLGRHYowJZaqYgjt9Lchmi3QYA/cXBsHK6VfWNR5jgA1DLhwfFe4HqfODBpINEECCLO47LT/+HSvSd/OCOgQ8qE0DbHQUBqpC4BkKMPYPkFY4iAJXhGAYr1qmaqQDbECCg5A2NMchzR567aA4xcRKclI405Bmt46vYD7/Gcjqfk6GP/kh1wovIDSHDfiAs/8bOCQ4cf4qMt7eH5Cucr3S0aWGFfjdLHD8EhCFvXQlSqRrY5UV2O9cfZtk77jUFMXeqzCEZqSK4ICkSin2tE12/3rbVcE41OBjBjBPSdJ1N5lfYQpIuhr8axnyIy5KvXmkYnw8VbcwtTNj7fDNCmT2kPQXA+bxpEXkB21HlnSQq0gD67jnfh5KavVJa/XQYEFSaagWwbgjNA+ywstLpEWTKgc5gwVpsyO1bTII+tA6B7BPS+0PiznuM9gPKsPVXbFdADMtwbJxSmkXWfRh6AZhyyzBjIHoDmnCGaMZAKjd5hyNJYCBGDOVcg28AXQ5atAVDO3c4dSALQnYblfa3M4kc/cyA7gMIUBQCTyl4kugIpy8yA7ACqK8Uwk30lIFGOEV3rPDAELwQkr/9YjkaCPDQhCcsrAYlF1v8W8jAEYeQDY7qn6tNGWudfq+YUEr6uq6FZzBpJMUfWFDatLHMCciw2mRC+k81qCCA1DzK4aUVfrJpxnloZWCPVnOgYy8L3GvKjE96HpweQoy7iwVQclVutLOEKJxA8gaRCjSzgNI2zhh3bQhzBCQQPIHGaHaUd96GJbZz3Smmjy16u6j3FuKyNxcBarxqWWfYFE0tVVO1Rl3t1Mb05V00MQCJ71YHpNaMcsjWAfkQvPPkaNC7LqTG7JAhGXTKYf+VDeXAX9IvURoAwtTFHvyYIxtnd5tPkywrPafcwbeSuGVwFau3b76NO7SHQrvqhfFE8kM0Wvpv8gVYiYBlxL+fW/34bgP6bIC7JR7YPDubcHCPzIp4+cum7U6NlhZgK7lua3KGLeFwE2m+HblDYWSHG2SAfINuwBBfxbJEIuWZbBH4fAExD7cvaGVyXyH0dhiAYc92z3ZDfUVv+jgb8HrHy7WVO/8BFcy9vuTz+nwADAGnOR39Yg/QkAAAAAElFTkSuQmCC" alt="Slim"/></a>
        </header>
        <h1>404! Recurso no encontrado.</h1>
    </body>
</html>
EOT;
        echo $template;
});
$app->run();
?>