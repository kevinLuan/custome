<%@ page language="java" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>订单管理</title>
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
<link rel="stylesheet" type="text/css" href="/custome/themes/default/easyui.css">
<link rel="stylesheet" type="text/css" href="/custome/themes/icon.css">

    <script type="text/javascript" src="/custome/scripts/jquery-1.4.2.min.js"></script>
    <script type="text/javascript" src="/custome/scripts/jquery.json-2.2.min.js"></script>
    <script type="text/javascript" src="/custome/scripts/jquery.easyui.min.js"></script>
    <script type="text/javascript" src="/custome/scripts/easyui-lang-zh_CN.js"></script>
    <script type="text/javascript" src="/custome/scripts/order/index.js?v=222"></script>
    <script type="text/javascript" src="/custome/scripts/custome.base.js"></script>
    <script type="text/javascript" src="/custome/scripts/validate.js"></script>
    <script type="text/javascript" src="/custome/scripts/DateUtils.js?123"></script>
    <style type="text/css">
       #showData span{
            cursor: hand;
            border:gray solid 1px;
            border-color: skyblue;
           font-size: 10px;
        }
        .btn{
            box-shadow: 1 2 3 4;border: gray solid 1px;
        }
        ._input{
        		width:200px;
        		height:30px;
        		font-size:24px;
        }
    </style>
      <style type="text/css">

            .box{
                width:500px;
                height:300px;
                border:1px solid #12B898;
                text-align: center;
                /*margin-top: auto;*/
                float: left;
                margin-left: 10px;
                margin-top: 10px;
                cursor: pointer;
                z-index: 1000;
                position: absolute;
                top: 50px;
                left: 50px;
                background-color: #fff8f8;
                padding: 10px;
                border: 10px solid #e3e3e3;

            }
          .btn{
              border: 1px solid #FFFFFF;
              cursor: pointer;
              border-radius: 5 5 5 5;
              display: inline-block;
              text-align: center;
              font-size: 20px;
          }
          .button {
                display: inline-block;
                position: relative;
                margin: 10px;
                padding: 0 20px;
                text-align: center;
                text-decoration: none;
                font: bold 12px/25px Arial, sans-serif;

                text-shadow: 1px 1px 1px rgba(255,255,255, .22);

                -webkit-border-radius: 30px;
                -moz-border-radius: 30px;
                border-radius: 30px;

                -webkit-box-shadow: 1px 1px 1px rgba(0,0,0, .29), inset 1px 1px 1px rgba(255,255,255, .44);
                -moz-box-shadow: 1px 1px 1px rgba(0,0,0, .29), inset 1px 1px 1px rgba(255,255,255, .44);
                box-shadow: 1px 1px 1px rgba(0,0,0, .29), inset 1px 1px 1px rgba(255,255,255, .44);

                -webkit-transition: all 0.15s ease;
                -moz-transition: all 0.15s ease;
                -o-transition: all 0.15s ease;
                -ms-transition: all 0.15s ease;
                transition: all 0.15s ease;
                background-color: royalblue;
                width: 60px;
                height: 25px;
            }
        </style>
</head>
<body style="margin-top: 2px">
<table id="tt"></table>
<div id="win" style="display:none">

<form id="objForm" method="POST" enctype="multipart/form-data" method="post">
<table align="center" style="width: 300px">
    <tr>
        <td></td>
        <td><input type="hidden" id="id" name="id" value="0"/></td>
    </tr>
    <tr>
    	<td>房间</td>
	    	<td>
	    		<select class="_input" id="roomId" name="roomId"  required="true" dataType="Require" msg="*">
	    			<option value="">--请选择--</option>
	    		</select>
    		</td>
    </tr>
    <tr>
	    	<td>人数</td>
	    	<td>
	    		<input class="_input" id="persionNum" name="persionNum" value="" maxlength="10" required="true" dataType="Number" msg="*" />
	    	</td>
    </tr>

    <tr>
        <td><span style="color:gray;">应收金额</span></td>
        <td>
            <input type="text" class="_input" id="totalPrice" name="totalPrice" value="" readonly="readonly" disabled="disabled"/>
        </td>
    </tr>
    <tr>
        <td><span name="brightText">实收金额</span></td>
        <td>
            <input type="text" class="_input" id="gather_price" name="gather_price" value=""/>
            <input type="hidden" class="_input" id="jieZhang" name="jieZhang">
        </td>
    </tr>
    <!--
        <tr>
            <td>结账</td>
            <td>
                <select class="_input" >
                    <option value="0" selected="selected">暂不结账</option>
                    <option value="1">结账完成</option>
                </select>
        </td>
    </tr>
     -->
    <tr>
        <td colspan="2" style="text-align: center">
            <button id="submit" class="button" style="width: 100px;">添加</button>
        </td>
    </tr>
</table>
</form>
</div>
<div id="win1"  title="添加" style="display:none">
     <ul id="win1tt"></ul>
</div>

<!-- 添加商品弹窗 -->
<div id="win_product" class="box" style="display:none">
	<form id="productForm" method="POST" enctype="multipart/form-data" method="post">
		<table align="center" style="width: 500px">
		    <tr>
		        <td></td>
		        <td><input type="hidden" id="addProductOrderId" name="addProductOrderId" value="0"/></td>
		    </tr>
		    <tr>
		    		<td>商品</td>
		    		<td>
		    		<table>
                    <tr>
                        <td rowspan="2">
                            <select class="_input" style="width: 200px;height: 250px" multiple="multiple" id="product_item" name="product_item" size="2">
                                <option value="">--请选择--</option>
                            </select>
                        </td>
                        <td><span style="cursor: pointer;color: darkgreen" title="添加项目" onclick="Easy.copyItem('product_item','product')">>></span></td>
                        <td rowspan="2"><select class="_input" style="width: 200px;height: 250px" id="product" name="product" size="2">
                        </select></td>
                    </tr>
                    <tr>
                        <td><span style="cursor: pointer;color: red;" title="减少项目" onclick="Easy.removeItem('product')"><<</span></td>
                    </tr>
					<tr>
						<td colspan="3" align="center">
                            <span class="button" onclick="saveProduct()">保存</span>
                            <span class="button" style="background-color: red;" onclick="closeWin('win_product')">关闭</span>
                        </td>
					</tr>
                </table>
		    		</td>
		    </tr>
		</table>
	</form>
</div>
<!-- 商品弹窗 -->


<!-- 添加套餐弹窗 -->
<div id="win_taocan" class="box" style="display:none">
    <form id="taocanForm" method="POST" enctype="multipart/form-data" method="post">
        <table align="center" style="width: 300px">
            <tr>
                <td></td>
                <td><input type="hidden" id="addTaocanOrderId" name="addTaocanOrderId" value="0"/></td>
            </tr>
            <tr>
                <td>套餐</td>
                <td>
                    <table>
                        <tr>
                            <td rowspan="2">
                                <select class="_input" style="width: 200px;height: 250px" multiple="multiple" id="taocan_item" name="taocan_item" size="2">
                                    <option value="">--请选择--</option>
                                </select>
                            </td>
                            <td><span style="cursor: pointer;color: darkgreen" title="添加项目" onclick="Easy.copyItem('taocan_item','taocan')">>></span></td>
                            <td rowspan="2"><select class="_input" style="width: 200px;height: 250px" id="taocan" name="taocan" size="2">
                            </select></td>
                        </tr>
                        <tr>
                            <td><span style="cursor: pointer;color: red;" title="减少项目" onclick="Easy.removeItem('taocan')"><<</span></td>
                        </tr>
                        <tr>
                            <td colspan="3" align="center">
                                <span class="button" onclick="saveTaocan()">保存</span>
                                <span class="button" style="background-color: red;" onclick="closeWin('win_taocan')">关闭</span>
                            </td>
                        </tr>
                    </table>
                </td>
            </tr>
        </table>
    </form>
</div>
<!-- 套餐弹窗结束 -->


<!-- 添加服务弹窗 -->
<div id="win_fuwu" class="box" style="display:none">
    <form id="fuwuForm" method="POST" enctype="multipart/form-data" method="post">
        <table align="center" style="width: 500px">
            <tr>
                <td></td>
                <td><input type="hidden" id="addServiceOrderId" name="addServiceOrderId" value="0"/></td>
            </tr>
            <tr>
                <td>服务项</td>
                <td>
                    <table>
                        <tr>
                            <td rowspan="2">
                                <select class="_input" style="width: 200px;height: 250px" multiple="multiple" id="service_item" name="service_item" size="2">
                                    <option value="">--请选择--</option>
                                </select>
                            </td>
                            <td><span style="cursor: pointer;color: darkgreen" title="添加项目" onclick="Easy.copyItem('service_item','service')">>></span></td>
                            <td rowspan="2"><select class="_input" style="width: 200px;height: 250px" id="service" name="service" size="2">
                            </select></td>
                        </tr>
                        <tr>
                            <td><span style="cursor: pointer;color: red;" title="减少项目" onclick="Easy.removeItem('service')"><<</span></td>
                        </tr>
                        <tr>
                            <td colspan="3" align="center">
                                <span class="button" onclick="saveService()">保存</span>
                                <span class="button" style="background-color: red;" onclick="closeWin('win_fuwu')">关闭</span>
                            </td>
                        </tr>
                    </table>
                </td>
            </tr>
        </table>
    </form>
</div>
<!-- 服务弹窗结束 -->


<!-- 保健师弹窗 -->
<div id="win_bjs" class="box" style="display:none">
    <form id="bjsForm" method="POST" enctype="multipart/form-data" method="post">
        <table align="center" id="bjs_table"  style="width: 500px">
            <tr>
                <td></td>
                <td><input type="hidden" id="addBjsOrderId" name="addBjsOrderId" value="0"/></td>
            </tr>
        </table>
    </form>
</div>
<!-- 保健师弹窗结束 -->

<script type="text/javascript">
    function closeWin(id){
        $("#"+id)[0].style.display="none";
        grid.datagrid('reload');
        grid.datagrid('clearSelections');
    }

    function saveBjs(){
        if(Validator.Validate($("#bjsForm")[0],3)){
            var bjs_sels= $("SELECT[id='bjs_sel']");
            var data={};
            data['orderId']=$("#addBjsOrderId").val();
            var array=[];
            for(var i=0;i<bjs_sels.length;i++){
                var map={};
                //服务ID
                map['id']= bjs_sels[i].getAttribute('data-id');
                //服务类型
                map['type']=bjs_sels[i].getAttribute('data-type');
                //保健师ID
                map['bjs_id']=bjs_sels[i].value;
                array.push(map);
            }
            data['items']=array;

            jQuery.ajax( {
                type : 'POST',
                contentType : 'application/json',
                url : '/custome/order/updateBjs.do?json='+JSON.stringify(data),
                data : "",
                dataType : 'json',
                success : function(res) {
                    closeWin('win_bjs');
//                    $.messager.alert("SUCCESS","成功成功!");
                    $.messager.show( {
                        title : '提示',
                        msg : '成功处理',
                        timeout : 3000,
                        showType : 'slide'
                    });
                    grid.datagrid('reload');
                },
                error : function() {
                    $.messager.alert('ERROR', '加载套餐数据出错', 'error');
                }
            })
        }
    }
    function openBjsWin(orderId){
        $("#win_bjs")[0].style.display="block";
        $("#addBjsOrderId").attr('value',orderId);
//        grid.datagrid('reload');
//        grid.datagrid('clearSelections');
        jQuery.ajax( {
            type : 'POST',
            contentType : 'application/json',
            url : '/custome/order/findBusinessByOrder.do?orderId='+orderId,
            data : "",
            dataType : 'json',
            success : function(res) {
                $("#bjs_table tr").eq(0).nextAll().remove();
                var result= res.result;
                var str="<tr><td>服务项目</td><td>保健师</td></tr>";
                for(var i=0;i<result.service_items.length;i++){
                    str+="<tr><td>";
                    str+=result.service_items[i].serviceName;
                    str+="</td>";
                    str+="<td><select id='bjs_sel' data-type='service' required='true' dataType='Require' msg='*' data-id='"+result.service_items[i].id+"' name='bjs_sel' style='width:180px'>";
                    str+="<option value=''>--请选择--</option>";
                    for(var j=0;j<result.bjs_items.length;j++){
                        if(result.service_items[i].bjsId==result.bjs_items[j].id){
                            str+="<option value='"+result.bjs_items[j].id+"' selected='selected'>"+result.bjs_items[j].employeNo+"</option>";
                        }else{
                            str+="<option value='"+result.bjs_items[j].id+"'>"+result.bjs_items[j].employeNo+"</option>";
                        }

                    }
                    str+="</select></td></tr>";
                }

                ;
                for(var i=0;i<result.taocan_items.length;i++){
                    str+="<tr><td>";
                    str+=result.taocan_items[i].taocanName;
                    str+='</td>';
                    str+="<td><select id='bjs_sel' name='bjs_sel' required='true' dataType='Require' msg='*' data-id='"+result.taocan_items[i].id+"' data-type='taocan' style='width:180px'>";
                    str+="<option value=''>--请选择--</option>";
                    for(var j=0;j<result.bjs_items.length;j++){
                        if(result.taocan_items[i].bjsId==result.bjs_items[j].id){
                            str+="<option value='"+result.bjs_items[j].id+"' selected='selected'>"+result.bjs_items[j].employeNo+"</option>";
                        }else{
                            str+="<option value='"+result.bjs_items[j].id+"'>"+result.bjs_items[j].employeNo+"</option>";
                        }

                    }
                    str+="</select></td></tr>";
                }
                var end_str='<tr>' +
                        '<td align="center" colspan="2">' +
                            '<span class="button" onclick="saveBjs()">保存</span>' +
                            '<span class="button" style="background-color: red;" onclick="closeWin(\'win_bjs\')">关闭</span>' +
                        '</td>' +
                        '</tr>';
                $("#bjs_table").append(str+end_str).show();

            },
            error : function() {
                $.messager.alert('ERROR', '加载保健师数据出错', 'error');
            }
        })
    }

    function openTaocanWin(orderId){
        $("#win_taocan")[0].style.display="block";
        $("#addTaocanOrderId").attr('value',orderId);
//        grid.datagrid('reload');
//        grid.datagrid('clearSelections');
        jQuery.ajax( {
            type : 'POST',
            contentType : 'application/json',
            url : '/custome/taocan_order/findTaocanByOrder.do?orderId='+orderId,
            data : "",
            dataType : 'json',
            success : function(res) {
                var taocan=$("#taocan");
                taocan[0].length=0;
                for(var i=0;i<res.result.length;i++){
                    taocan.append('<option value="'+res.result[i].id+'">'+res.result[i].name+'</option>');
                }
            },
            error : function() {
                $.messager.alert('ERROR', '加载套餐数据出错', 'error');
            }
        })
    }

    function openProductWin(orderId){
        $("#win_product")[0].style.display="block";
        $("#addProductOrderId").attr('value',orderId);
//        grid.datagrid('reload');
//        grid.datagrid('clearSelections');

        jQuery.ajax( {
            type : 'POST',
            contentType : 'application/json',
            url : '/custome/order_product/findProductByOrder.do?orderId='+orderId,
            data : "",
            dataType : 'json',
            success : function(res) {
                var product=$("#product");
                product[0].length=0;
                for(var i=0;i<res.result.length;i++){
                    product.append('<option value="'+res.result[i].id+'">'+res.result[i].name+'</option>');
                }
            },
            error : function() {
                $.messager.alert('ERROR', '加载订单商品数据出错', 'error');
            }
        })
    }

    function openServiceWin(orderId){
        $("#win_fuwu")[0].style.display="block";
        $("#addServiceOrderId").attr('value',orderId);
//        grid.datagrid('reload');
//        grid.datagrid('clearSelections');

        jQuery.ajax( {
            type : 'POST',
            contentType : 'application/json',
            url : '/custome/service_order/findServiceByOrder.do?orderId='+orderId,
            data : "",
            dataType : 'json',
            success : function(res) {
                var service=$("#service");
                service[0].length=0;
                for(var i=0;i<res.result.length;i++){
                    service.append('<option value="'+res.result[i].id+'">'+res.result[i].name+'</option>');
                }
            },
            error : function() {
                $.messager.alert('ERROR', '加载服务数据出错', 'error');
            }
        })
    }


	var Easy={
            init:function(){
//                Easy.loadRoom();
                Easy.loadTaocan();
                Easy.loadServiceItem();
                Easy.loadProduct();
                Easy.loadBjs();

            },
			loadRoom:function(){
				jQuery.ajax( {
					type : 'POST',
					contentType : 'application/json',
					url : '/custome/room/findAllIdleRoom.do',
					data : "",
					dataType : 'json',
					success : function(data) {
						var room=$("#roomId");
                        var val= room.val();
						room[0].length=0;
						room.append('<option value="">--请选择--</option>');
						for(var i=0;i<data.rows.length;i++){
                            room.append('<option value="'+data.rows[i].id+'">'+data.rows[i].name+'</option>');
						}
                        room.attr('value',val);
					},
					error : function() {
                        $.messager.show( {
                            title : '警告',
                            msg : '加载房间数据出错',
                            timeout : 5000,
                            showType : 'slide'
                        });
					}
				})
            },
            loadAllRoom:function(callback){
                jQuery.ajax( {
                    type : 'POST',
                    contentType : 'application/json',
                    url : '/custome/room/findAll.do',
                    data : "",
                    dataType : 'json',
                    success : function(data) {
                        var room=$("#roomId");
                        var val= room.val();
                        room[0].length=0;
                        room.append('<option value="">--请选择--</option>');
                        for(var i=0;i<data.rows.length;i++){
                            room.append('<option value="'+data.rows[i].id+'">'+data.rows[i].name+'</option>');
                        }
                        room.attr('value',val);
                        if(callback && typeof callback== 'function'){
                            callback();
                        }
                    },
                    error : function() {
                        $.messager.show( {
                            title : '警告',
                            msg : '加载房间数据出错',
                            timeout : 5000,
                            showType : 'slide'
                        });
                    }
                })
            },
            loadTaocan:function(){
                    jQuery.ajax( {
                        type : 'POST',
                        contentType : 'application/json',
                        url : '/custome/taocan/findAllValid.do',
                        data : "",
                        dataType : 'json',
                        success : function(data) {
                            var product_item=$("#taocan_item");
                            var val= product_item.val();
                            if(product_item.length>0){
                                product_item[0].length=0;
                            }
                            for(var i=0;i<data.rows.length;i++){
                                product_item.append('<option value="'+data.rows[i].id+'">'+data.rows[i].name+'</option>');
                            }
                            product_item.attr('value',val);
                        },
                        error : function() {
                            $.messager.show( {
                                title : '警告',
                                msg : '加载套餐数据出错',
                                timeout : 5000,
                                showType : 'slide'
                            });
                        }
                    });
			},
            loadServiceItem:function(){
                jQuery.ajax( {
                    type : 'POST',
                    contentType : 'application/json',
                    url : '/custome/service/findAllValid.do?page=1&rows=1000',
                    data : "",
                    dataType : 'json',
                    success : function(data) {
                        var service_item=$("#service_item");
                        var val= service_item.val();
                        service_item[0].length=0;
                        for(var i=0;i<data.rows.length;i++){
                            service_item.append('<option value="'+data.rows[i].id+'">'+data.rows[i].name+'</option>');
                        }
                        service_item.attr('value',val);
                    },
                    error : function() {
                        $.messager.show( {
                            title : '警告',
                            msg : '加载服务数据出错',
                            timeout : 5000,
                            showType : 'slide'
                        });
                    }
                });
            },
        loadProduct:function(){
            jQuery.ajax( {
                type : 'POST',
                contentType : 'application/json',
                url : '/custome/product/findAll.do?page=1&rows=1000',
                data : "",
                dataType : 'json',
                success : function(data) {
                    var product_item=$("#product_item");
                    var val= product_item.val();
                    if(product_item.length>0){
                    		product_item[0].length=0;	
                    }
                    for(var i=0;i<data.rows.length;i++){
                        product_item.append('<option value="'+data.rows[i].id+'">'+data.rows[i].name+'</option>');
                    }
                    product_item.attr('value',val);
                },
                error : function() {
                    $.messager.show( {
                        title : '警告',
                        msg : '加载商品数据出错',
                        timeout : 5000,
                        showType : 'slide'
                    });
                }
            });
        },
        loadBjs:function(){
            jQuery.ajax( {
                type : 'POST',
                contentType : 'application/json',
                url : '/custome/baojianshi/findAllIdle.do',
                data : "",
                dataType : 'json',
                success : function(data) {
                    var bjs_list=$("#bjs_list");
                    var val= bjs_list.val();
                    if(bjs_list[0]){
                        bjs_list[0].length=0;
                    }
                    for(var i=0;i<data.rows.length;i++){
                        bjs_list.append('<option value="'+data.rows[i].id+'">'+data.rows[i].employeNo+'</option>');
                    }
                    bjs_list.attr('value',val);
                },
                error : function() {
                    $.messager.show( {
                        title : '警告',
                        msg : '加载保健师数据出错',
                        timeout : 5000,
                        showType : 'slide'
                    });
                }
            });
        },
        removeItem:function(src){
            if($("#"+src).get(0).selectedIndex<0){
                alert('请选择');
                return;
            }

            var val= $("#"+src).val();
                // 这种方式删除会把所有option value 相同的删除
//            $("#"+src+" option[value='"+val+"']").remove();
             // 删除option value 相同的第一项
            $($("#"+src+" option[value='"+val+"']")[0]).remove();
        },
        copyItem:function(src,dest){
            if($('#'+src).val()){
                var cols= $("#"+src).val();
                for(var i=0;i<cols.length;i++){
                    var item= $("#"+src+" option[value='"+cols[i]+"']");
                    $("#"+dest).append('<option value='+item.val()+'>'+item.text()+'</option>')

                }
            }
        }

	}
	Easy.init();

    setInterval(Easy.init,2000);

    function saveProduct(){
        var options= $("#product")[0].options;
//        if(options.length==0){
//            alert('请添加商品');
//            return;
//        }
        var array={};
        for(var i=0;i<options.length;i++){
            if(array[options[i].value]){
                array[options[i].value]++;
            }else{
                array[options[i].value]=1;
            }

        }
        var orderId=$("#addProductOrderId").val();
        jQuery.ajax( {
            type : 'POST',
            contentType : 'application/json',
            url : '/custome/order_product/addProduct.do?orderId='+orderId+'&json='+JSON.stringify(array),
            data : "",
            dataType : 'json',
            success : function(data) {
                $.messager.show( {
                    title : '提示',
                    msg : '成功处理',
                    timeout : 3000,
                    showType : 'slide'
                });
                grid.datagrid('reload');
                $("#win_product")[0].style.display="none";
            },
            error : function() {
                $.messager.alert('ERROR', '添加商品失败', 'error');
            }
        });
    }


    function saveService(){
        var options= $("#service")[0].options;
//        if(options.length==0){
//            alert('请添加服务项目');
//            return;
//        }
        var array={};
        for(var i=0;i<options.length;i++){
            if(array[options[i].value]){
                array[options[i].value]++;
            }else{
                array[options[i].value]=1;
            }

        }
        var orderId=$("#addServiceOrderId").val();
        jQuery.ajax( {
            type : 'POST',
            contentType : 'application/json',
            url : '/custome/service_order/addService.do?orderId='+orderId+'&json='+JSON.stringify(array),
            data : "",
            dataType : 'json',
            success : function(data) {
                $.messager.show( {
                    title : '提示',
                    msg : '成功处理',
                    timeout : 3000,
                    showType : 'slide'
                });
                grid.datagrid('reload');
                $("#win_fuwu")[0].style.display="none";
            },
            error : function() {
                $.messager.alert('ERROR', '添加服务失败', 'error');
            }
        });
    }

    function saveTaocan(){
        var options= $("#taocan")[0].options;
//        if(options.length==0){
//            alert('请添加套餐项目');
//            return;
//        }
        var array={};
        for(var i=0;i<options.length;i++){
            if(array[options[i].value]){
                array[options[i].value]++;
            }else{
                array[options[i].value]=1;
            }

        }
        var orderId=$("#addTaocanOrderId").val();
        jQuery.ajax( {
            type : 'POST',
            contentType : 'application/json',
            url : '/custome/taocan_order/addTaocan.do?orderId='+orderId+'&json='+JSON.stringify(array),
            data : "",
            dataType : 'json',
            success : function(data) {
                $.messager.show( {
                    title : '提示',
                    msg : '成功处理',
                    timeout : 3000,
                    showType : 'slide'
                });
                grid.datagrid('reload');
                $("#win_taocan")[0].style.display="none";
            },
            error : function() {
                $.messager.alert('ERROR', '添加套餐失败', 'error');
            }
        });
    }
</script>
</body>
</html>