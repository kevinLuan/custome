<%@ page language="java" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>保健师管理</title>
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
    <script type="text/javascript" src="/custome/scripts/order/index.js?v=222332"></script>
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
        		width:300px;
        		height:30px;
        		font-size:24px;
        }
    </style>
</head>
<body style="margin-top: 2px">
<table id="tt"></table>
<div id="win" style="display:none">

<form id="objForm" method="POST" enctype="multipart/form-data" method="post">
<table align="center">
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
	    		<input class="_input" id="persionNum" name="persionNum" value="" maxlength="10" required="true" dataType="Require" msg="*" />
	    	</td>
    </tr>
    <tr>
    		<td>套餐</td>
    		<td>
                <input type="hidden" id="taoCanId" name="taoCanId"/>
                <table>
                    <tr>
                        <td rowspan="2">
                            <select class="_input" style="width: 150px;height: 150px" id="taocan_item" name="taocan_item" size="2">
                                <option value="">--请选择--</option>
                            </select>
                        </td>
                        <td><span style="cursor: pointer;color: darkgreen" title="添加项目" onclick="Easy.copyItem('taocan_item','taocan')">>></span></td>
                        <td rowspan="2"><select class="_input" style="width: 150px;height: 150px" id="taocan" name="taocan" size="2">
                        </select></td>
                    </tr>
                    <tr>
                        <td><span style="cursor: pointer;color: red;" title="减少项目" onclick="Easy.removeItem('taocan')"><<</span></td>
                    </tr>

                </table>
    		</td>
    </tr>
    <tr>
    		<td>服务项目</td>
    		<td>
                <input type="hidden" id="serviceId" name="serviceId"/>
                <table>
                    <tr>
                        <td rowspan="2">
                            <select class="_input" style="width: 150px;height: 150px" id="service_item" name="service_item" size="2">
                                <option value="">--请选择--</option>
                            </select>
                        </td>
                        <td><span style="cursor: pointer;color: darkgreen" title="添加项目" onclick="Easy.copyItem('service_item','service')">>></span></td>
                        <td rowspan="2"><select class="_input" style="width: 150px;height: 150px" id="service" name="service" size="2">
                        </select></td>
                    </tr>
                    <tr>
                        <td><span style="cursor: pointer;color: red;" title="减少项目" onclick="Easy.removeItem('service')"><< </span></td>
                    </tr>

                </table>




            </td>
    </tr>
    <tr>
    		<td>消费商品</td>
    		<td>
                <input type="hidden" type="text" id="productId" name="productId"/>
                <table>
                    <tr>
                        <td rowspan="2">
                            <select class="_input" style="width: 150px;height: 150px" id="product_item" name="product_item" size="2">
                                <option value="">--请选择--</option>
                            </select>
                        </td>
                        <td><span style="cursor: pointer;color: darkgreen" title="添加项目" onclick="Easy.copyItem('product_item','product')">>></span></td>
                        <td rowspan="2"><select class="_input" style="width: 150px;height: 150px" id="product" name="product" size="2">
                        </select></td>
                    </tr>
                    <tr>
                        <td><span style="cursor: pointer;color: red;" title="减少项目" onclick="Easy.removeItem('product')"><<</span></td>
                    </tr>

                </table>
            </td>
    </tr>
    <tr>
    		<td>保健师</td>
    		<td>
                <input type="hidden" type="text" id="bjsId" name="bjsId"/>
                <table>
                    <tr>
                        <td rowspan="2">
                            <select class="_input" style="width: 150px;height: 150px" id="bjs_list" name="bjs_list" size="2">
                                <option value="">--请选择--</option>
                            </select>
                        </td>
                        <td><span style="cursor: pointer;color: darkgreen" title="添加项目" onclick="Easy.copyItem('bjs_list','bjs')">>></span></td>
                        <td rowspan="2"><select class="_input" style="width: 150px;height: 150px" id="bjs" name="bjs" size="2">
                        </select></td>
                    </tr>
                    <tr>
                        <td><span style="cursor: pointer;color: red;" title="减少项目" onclick="Easy.removeItem('bjs')"><<</span></td>
                    </tr>

                </table>
            </td>
    </tr>
    <tr>
    		<td>结账</td>
    		<td>
    			<select class="_input" id="jieZhang" name="jieZhang">
    				<option value="0">暂不结账</option>
    				<option value="1">结账完成</option>
    			</select>
    		</td>
    </tr>
</table>
</form>
</div>
<div id="win1"  title="添加" style="display:none">
     <ul id="win1tt"></ul>
</div>
<script type="text/javascript">
	var Easy={
            init:function(){
                Easy.loadRoom();
                Easy.loadTaocan();
                Easy.loadServiceItem();
                Easy.loadProduct();
                Easy.loadBjs();
                Easy.loadTaocan();
            },
			loadRoom:function(){
				jQuery.ajax( {
					type : 'POST',
					contentType : 'application/json',
					url : '/custome/room/findAll.do?page=1&rows=1000',
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
						alert('加载房间数据出错');
					}
				})
            },
            loadTaocan:function(){
                    jQuery.ajax( {
                        type : 'POST',
                        contentType : 'application/json',
                        url : '/custome/taocan/findAll.do?page=1&rows=1000',
                        data : "",
                        dataType : 'json',
                        success : function(data) {
                            var taoCanId=$("#taoCanId");
                            var val= taoCanId.val();
                            taoCanId[0].length=0;
                            taoCanId.append('<option value="">--请选择--</option>');
                            for(var i=0;i<data.rows.length;i++){
                                taoCanId.append('<option value="'+data.rows[i].id+'">'+data.rows[i].name+'</option>');
                            }
                            taoCanId.attr('value',val);
                        },
                        error : function() {
                            alert('加载套餐数据出错');
                        }
                    });
			},
            loadServiceItem:function(){
                jQuery.ajax( {
                    type : 'POST',
                    contentType : 'application/json',
                    url : '/custome/service/findAll.do?page=1&rows=1000',
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
                        alert('加载套餐数据出错');
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
                    product_item[0].length=0;
                    for(var i=0;i<data.rows.length;i++){
                        product_item.append('<option value="'+data.rows[i].id+'">'+data.rows[i].name+'</option>');
                    }
                    product_item.attr('value',val);
                },
                error : function() {
                    alert('加载商品数据出错');
                }
            });
        },
        loadBjs:function(){
            jQuery.ajax( {
                type : 'POST',
                contentType : 'application/json',
                url : '/custome/baojianshi/findAll.do?page=1&rows=1000',
                data : "",
                dataType : 'json',
                success : function(data) {
                    var bjs_list=$("#bjs_list");
                    var val= bjs_list.val();
                    bjs_list[0].length=0;
                    for(var i=0;i<data.rows.length;i++){
                        bjs_list.append('<option value="'+data.rows[i].id+'">'+data.rows[i].employeNo+'</option>');
                    }
                    bjs_list.attr('value',val);
                },
                error : function() {
                    alert('加载保健师数据出错');
                }
            });
        },
        loadTaocan:function(){
            jQuery.ajax( {
                type : 'POST',
                contentType : 'application/json',
                url : '/custome/taocan/findAll.do?page=1&rows=1000',
                data : "",
                dataType : 'json',
                success : function(data) {
                    var taocan_item=$("#taocan_item");
                    var val= taocan_item.val();
                    taocan_item[0].length=0;
                    for(var i=0;i<data.rows.length;i++){
                        taocan_item.append('<option value="'+data.rows[i].id+'">'+data.rows[i].name+'</option>');
                    }
                    taocan_item.attr('value',val);
                },
                error : function() {
                    alert('加载套餐数据出错');
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
            if($('#'+src).val().length>0){
                var val=$('#'+src).val();
                var sel= $('#'+src)[0];
                var text=sel[sel.selectedIndex].text;
                $("#"+dest).append('<option value='+val+'>'+text+'</option>')
            }else{
                alert('请选择');
            }
        }

	}
	Easy.init();

    setInterval(Easy.init,100000);


</script>
</body>
</html>