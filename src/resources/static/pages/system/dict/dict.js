;(function () {
  layui.extend({treeTable: ctxUrl + '/assets/lib/tree_table/tree_table.min'})
    .use(['treeTable', 'form', 'layer', 'jquery'], function () {
      const treeTable = layui.treeTable
        , form = layui.form
        , layer = layui.layer
        , $ = layui.jquery
        , loadId = utils.loadingFun()
        , insTb = treeTable.render({
        elem: '#treeTableDict',
        data: [],
        tree: {
          iconIndex: 2,  // 折叠图标显示在第几+1列
          idName: "dictId",
          childName: "children",
          getIcon: function (rowData) {
            // 判断是否有children
            /* return rowData.children && rowData.children.length !== 0 ?
                 '<i class="ew-tree-icon ew-tree-icon-folder"></i>' :
                 '<i class="ew-tree-icon ew-tree-icon-file"></i>'*/
            return `<i class="fa ${ rowData.icon ? rowData.icon : "fa-dot-circle-o" }"> `
          }
        },
        cols: [
          {type: 'radio'},
          {type: 'numbers', title: '序号', align: 'center', width: 60},
          {field: 'dictLabel', title: '字典名称', width: 160},
          {field: 'dictSort', title: '排序', align: 'center', width: 60},
          {field: 'dictType', title: '字典类型', width: 160},
          {field: 'dictValue', title: '字典值', width: 160},
          {field: 'remark', title: '字典备注', width: 180},
          {
            title: '操作', unresize: true, align: 'center',
            templet: `<div><a class="layui-btn layui-btn-xs layui-btn-normal" lay-event="edit"><i class="fa fa-edit"></i> 编辑</a>
<a class="layui-btn layui-btn-xs" lay-event="add"><i class="fa fa-trash-o"></i> 新增</a>
<a class="layui-btn layui-btn-xs layui-btn-danger" lay-event="delete"><i class=" fa fa-trash"></i> 删除</a></div>`
          }
        ]
      })
      /** form表单渲染函数 */
      formRender()
      /** treeTable表单初始化 */
      requestDictList()
      $('#expandAndCollapse').click(function () {
        let flag = $(this).hasClass('expand')
        $(this).toggleClass('expand')
        flag ? insTb.foldAll('#treeTableDict') : insTb.expandAll('#treeTableDict')
      })
      $('#rootDictAdd').click(dictAdd)

      treeTable.on('tool(treeTableDict)', function ({event, data}) {
        switch (event) {
          case 'edit':
            dictEdit(data)
            break
          case 'add':
            dictAdd(data)
            break
          case 'delete':
            dictDelete(data)
            break
        }
      })

      // 关闭加载层
      layer.close(loadId)
      /** 字典删除 */
      function dictDelete(data) {
        const rootIdList = []
        rootIdList.push(data['dictId'])
        if (Array.isArray(data['children']) && data['children'].length) {
          getDictIdList({dataList: data['children'], resList: rootIdList})
        }
        layer.open({
          title: '系统提示',
          content: '是否删除字典：' + data['dictType'],
          move: false, shadeClose: true, skin: 'self-class',
          yes: function (index) {
            $.ajax(
              {
                url: ctxUrl + '/sys_dict_data/delete_dict_data'
                , method: 'post', data: {dictIdList: rootIdList}
              })
              .done(function ({code, message, data}) {
                layer.msg(data, {time: 1000})
                layer.close(index)
                requestDictList()
              })
              .fail(function () { window.location.reload()})
          }
        })

        /**
         * 递归获取dictId存入数组中
         */
        function getDictIdList({dataList, resList}) {
          for (let datum of dataList) {
            resList.push(datum['dictId'])
            if (Array.isArray(datum['children']) && datum['children'].length) {
              getDictIdList({dataList: datum['children'], resList: rootIdList})
            }
          }
        }
      }
      /** 字典增加 */
      function dictAdd(data) {
        let url = `/sys_dict_data/dict_add/#/`,
          argArr = []
        data['parentId'] ? argArr.push('parentId=' + data['parentId'])
          : argArr.push('parentId=' + data['dictId'])
        utils.openNewPage(['字典添加', url + argArr.join('&')])
      }
      /** 字典编辑 */
      function dictEdit(data) {
        let url = `/sys_dict_data/dict_edit/#/&`,
          argArr = []
        data['parentId'] ? argArr.push('parentId=' + data['parentId']) : ''
        data['dictId'] ? argArr.push('dictId=' + data['dictId']) : ''
        utils.openNewPage(['字典编辑', url + argArr.join('&')])
      }
      /** form表单渲染函数 */
      function formRender() {
        // 请求根字典数据
        $.ajax({url: ctxUrl + '/sys_dict_data/root_dict', method: 'post', async: false})
          .done(function ({data}) {
            let $select = $("select[name='parentType']")
            let tempHTML = '<option value="" selected>全部标签</option>'
            for (let item of data) { tempHTML += `<option value="${ item['dictType'] }">${ item['dictLabel'] }</option>`}
            $select.append(tempHTML)
            form.render('select')
          })
          .fail(function () {window.location.reload()})
        // 监听form事件
        form.on('submit(search-submit)', function ({field}) {
          requestDictList(field)
          return false
        })
      }

      /** treeTable表单更新 */
      function reloadTreeTable(data) {
        utils.arrayMenuSort({list: data, sortName: "dictSort"})
        insTb.reload({data})
      }

      /** 请求字典List值 */
      function requestDictList(field) {
        $.ajax({url: ctxUrl + '/sys_dict_data/dict_list', method: 'post', data: field, async: false})
          .done(function ({data}) {reloadTreeTable(data)})
          .fail(function () {window.location.reload()})
      }
    })
})()