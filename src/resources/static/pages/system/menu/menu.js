;(function () {
  layui.extend({treeTable: ctxUrl + '/assets/lib/tree_table/tree_table.min'})
    .use(['treeTable', 'layer', 'jquery'], function () {
      const treeTable = layui.treeTable
        , layer = layui.layer
        , $ = layui.jquery
        , loadId = utils.loadingFun()
      const insTb = treeTable.render({
        elem: '#treeTableMenu',
        data: [],
        tree: {
          iconIndex: 2,  // 折叠图标显示在第几+1列
          idName: "menuId",
          childName: "children",
          getIcon: function ({icon}) {
            return `<i class="fa ${ icon ? icon : "fa-dot-circle-o" }"> `
          }
        },
        cols: [
          {type: 'radio'},
          {type: 'numbers', title: '序号', align: 'center', width: 60},
          {field: 'menuName', title: '菜单名称', width: 160},
          {field: 'orderNum', title: '排序', align: 'center', width: 60},
          {field: 'path', title: '请求地址', width: 260},
          {
            field: 'isFrame', title: '打开方式', width: 100,
            templet: function ({isFrame, menuType}) {
              if (menuType === 'M') {return `<div style="background-color: #009688;text-align: center;color: #FFFFFF">菜单目录</div>` }
              return isFrame === '1' ?
                `<div style="background-color: #1E9FFF;text-align: center;color: #FFFFFF">选项打开</div>` :
                `<div style="background-color: #FF843F;text-align: center;color: #FFFFFF">外链打开</div>`
            }
          },
          {field: 'remark', title: '菜单备注', width: 180},
          {
            title: '操作', unresize: true, align: 'center',
            templet: `<div><a class="layui-btn layui-btn-xs layui-btn-normal" lay-event="edit"><i class="fa fa-edit"></i> 编辑</a>
<a class="layui-btn layui-btn-xs" lay-event="add"><i class="fa fa-trash-o"></i> 新增</a>
<a class="layui-btn layui-btn-xs layui-btn-danger" lay-event="delete"><i class=" fa fa-trash"></i> 删除</a></div>`
          }
        ]
      })
      /** form表单渲染函数 */
      treeTableRender()
      $('fieldset.layui-elem-field button.layui-btn').click(function () {
        let tempKey = $('fieldset.layui-elem-field input[name="menuName"]').val()
        insTb.filterData(tempKey)
      })
      $('#expandAndCollapse').click(function () {
        let flag = $(this).hasClass('expand')
        $(this).toggleClass('expand')
        flag ? insTb.foldAll('#treeTableMenu') : insTb.expandAll('#treeTableMenu')
      })
      layer.close(loadId)
      treeTable.on('tool(treeTableMenu)', function ({event, data}) {
        switch (event) {
          case 'edit':
            menuEdit(data)
            break
          case 'add':
            menuAdd(data)
            break
          case 'delete':
            menuDelete(data)
            break
        }
      })
      $('#rootMenuAdd').click(menuAdd)
      /** form表单渲染函数 */
      function treeTableRender() {
        $.ajax({url: ctxUrl + '/sys_menu/menu_list', method: 'post', async: false})
          .done(function ({data}) {
            utils.arrayMenuSort({list: data})
            insTb.reload({data})
          })
          .fail(function () {window.location.reload()})
      }

      /** 菜单删除 */
      function menuDelete(data) {
        const rootIdList = []
        rootIdList.push(data['menuId'])
        if (Array.isArray(data['children']) && data['children'].length) {
          getDictIdList({dataList: data['children'], resList: rootIdList})
        }
        layer.open({
          title: '系统提示',
          content: '是否删除菜单：' + data['menuName'],
          move: false, shadeClose: true, skin: 'self-class',
          yes: function (index) {
            $.ajax(
              {
                url: ctxUrl + '/sys_menu/delete_menu'
                , method: 'post', data: {menuIdList: rootIdList}
              })
              .done(function ({code, message, data}) {
                layer.msg(data, {time: 1000})
                layer.close(index)
                treeTableRender()
              })
              .fail(function () { window.location.reload()})
          }
        })

        /**递归获取menuId存入数组中*/
        function getDictIdList({dataList, resList}) {
          for (let datum of dataList) {
            resList.push(datum['menuId'])
            if (Array.isArray(datum['children']) && datum['children'].length) {
              getDictIdList({dataList: datum['children'], resList: rootIdList})
            }
          }
        }
      }

      /** 菜单新增 */
      function menuAdd(data) {
        let url = `/sys_menu/menu_add/#/&`,
          argArr = []
        if ('C' === data['menuType']) {
          data['parentId'] ? argArr.push('parentId=' + data['parentId']) : ''
          data['parentName'] ? argArr.push('parentName=' + data['parentName']) : ''
        }
        if ('M' === data['menuType']) {
          data['menuId'] ? argArr.push('parentId=' + data['menuId']) : ''
          data['menuName'] ? argArr.push('parentName=' + data['menuName']) : ''
        }
        utils.openNewPage(['菜单新增', url + argArr.join('&')])
      }

      /** 菜单编辑 */
      function menuEdit(data) {
        let url = `/sys_menu/menu_edit/#/`,
          argArr = []
        data['menuId'] ? argArr.push('menuId=' + data['menuId']) : argArr.push('menuId=' + data['menuId'])
        utils.openNewPage(['菜单编辑', url + argArr.join('&')])
      }
    })
})()