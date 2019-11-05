<%@page pageEncoding="UTF-8" %>
<script>
    $(function () {
        $("#star-show-table").jqGrid({
            url : '${pageContext.request.contextPath}/star/selectAll',
            datatype : "json",
            height : 300,
            colNames : [ '编号', '艺名', '真名', '照片', '性别', '生日' ],
            colModel : [
                {name : 'id'},
                {name : 'nickname'},
                {name : 'realname'},
                {name : 'photo',formatter:function (value,option,rows) {
                        return "<img style='width:100px;height:70px' src='${pageContext.request.contextPath}/star/img/"+rows.photo+"'>";
                    }},
                {name : 'sex'},
                {name : 'bir'}
            ],
            styleUI:'Bootstrap',
            autowidth:true,
            rowNum : 3,
            rowList : [ 3, 5, 10],
            pager : '#star-page',
            viewrecords : true,
            subGrid : true,
            caption : "所有明星列表",
            subGridRowExpanded : function(subgrid_id, id) {
                var subgrid_table_id, pager_id;
                subgrid_table_id = subgrid_id + "_t";
                pager_id = "p_" + subgrid_table_id;
                $("#" + subgrid_id).html(
                    "<table id='" + subgrid_table_id  +"' class='scroll'></table>" +
                    "<div id='" + pager_id + "' class='scroll'></div>");
                $("#" + subgrid_table_id).jqGrid(
                    {
                        url : "${pageContext.request.contextPath}/user/selectUsersByStarId?starId=" + id,
                        datatype : "json",
                        colNames : [ '编号', '用户名', '昵称', '头像','电话', '性别','地址','签名' ],
                        colModel : [
                            {name : "id"},
                            {name : "username"},
                            {name : "nickname"},
                            {name : "photo"},
                            {name : "phone"},
                            {name : "sex"},
                            {name : "address"},
                            {name : "sign"}
                        ],
                        styleUI:"Bootstrap",
                        rowNum : 2,
                        pager : pager_id,
                        autowidth:true,
                        height : '100%'
                    });
                jQuery("#" + subgrid_table_id).jqGrid('navGrid',
                    "#" + pager_id, {
                        edit : false,
                        add : false,
                        del : false,
                        search:false
                    });
            },

        }).jqGrid('navGrid', '#star-page', {
            add : true,
            edit : false,
            del : false,
            search:false
        });
    })
</script>


<div class="panel page-header">
    <h3>展示所有的明星</h3>
</div>
<table id="star-show-table"></table>
<div id="star-page" style="height: 40px;"></div>