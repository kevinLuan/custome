var userTree;

$(function() {
    userTree=$('#mytree');
    userTree.accordion();
    jQuery.ajax( {
		type : 'POST',
		contentType : 'application/json',
		url :'./role/getuserRoles.do',
		dataType : 'json',
		success : function(data) {
			$.each(data,function(entryIndex,entry){ 
			    userTree.accordion('add',{
				title:entry.text,
				content:"<ul id=tree"+entryIndex+"></ul>"
			    });
			    $('#tree'+entryIndex).tree({
			       data:entry.children,
			      onClick:function(node){
	                  if(node.attributes.url!=null){
	                      addTab(node.text,node.attributes.url);
	                   }
	              } 
			    });
             }); 
		},
		error : function() {
			$.messager.alert('ERROR', '操作失败!', 'error');
		}
	});
});

