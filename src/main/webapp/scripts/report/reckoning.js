var grid;
var win;
var form;
var win1;

$(function() {
    reload_grid=function(url,startDate,endDate){
        if(startDate && endDate){
            url+='?startDate='+startDate+'&endDate='+endDate;
        }

        grid = $('#tt').datagrid( {
            iconCls : 'icon-save',
            loadMsg : '数据加载中，请稍后......',
            nowrap : false,
            striped : true,
            rownumbers : true,
            singleSelect : true,
            pagination : false,
            pageList : [ 20,30,40 ],
            url : url,
            idField : 'id',
            columns : [  [
            {
                title : '保健师ID',
                field : 'bjs_id',
                width : 200
            },{
                title:'保健师号',
                field:'employe_no',
                width:200
            },{
                    title:'保健师姓名',
                    field:'name',
                    width:200
            },{
                title:'总金额',
                field:'price',
                width:200
            }
            ] ],
            toolbar : [ ]
        });
        loadMenu(startDate,endDate);
    },
    loadMenu=function(startDate,endDate){
        if(endDate==undefined){
        		endDate='';
        }
        if(startDate==undefined){
        		startDate='';
        }
        $(".datagrid-toolbar").append(
            '<div>' +
            '开始日期：<input type="text" id="startDate" name="startDate" value="'+startDate+'" maxlength="10" readonly="readonly" onclick="WdatePicker({lang:\'zh-cn\',isShowClear:false,dateFmt:\'yyyy-MM-dd\',minDate:\'2015-10-01\',maxDate:\'\'}  )" dataType="Require" msg="*"/>' +
            '结束日期：<input type="text" id="endDate" name="endDate" value="'+endDate+'" maxlength="10" readonly="readonly" onclick="WdatePicker({lang:\'zh-cn\',isShowClear:false,dateFmt:\'yyyy-MM-dd\',minDate:\'2015-10-01\',maxDate:\'\'}  )" dataType="Require" msg="*"/>'+
            '<button id="submit">查询</button><button id="exportHtml">导出Excel</button>'+
            '</div>' +
            '');
        $("#submit").bind('click',function(){
            var startDate=$("#startDate").val();
            var endDate= $("#endDate").val();
            if(startDate.length==0){
                $.messager.alert('ERROR', '请选择开始日期', 'error');
                return;
            }
            if(endDate.length==0){
                $.messager.alert('ERROR', '请选择结束日期', 'error');
                return;
            }
        	reload_grid('./stat_bjs_reckoning.do',$("#startDate").val(),$("#endDate").val());
        })
        $("#exportHtml").bind('click',function(){
            var startDate=$("#startDate").val();
            var endDate= $("#endDate").val();
            if(startDate.length==0){
                $.messager.alert('ERROR', '请选择开始日期', 'error');
                return;
            }
            if(endDate.length==0){
                $.messager.alert('ERROR', '请选择结束日期', 'error');
                return;
            }
            window.location.href='bjs_reckoning.html?startDate='+startDate+'&endDate='+endDate+'&downloadFileName=bjs_reckoning.xls';
        })
    }
    reload_grid('./bjs_reckoning.do');
});