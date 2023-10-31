;(function () {
  layui.use(['layer', 'jquery', 'form'], function () {
    const $ = layui.jquery
      , layer = layui.layer
      , form = layui.form
      , loadId = utils.loadingFun()
      , router = layui.router()
      , nowMenuId = router.search.menuId
    $.when(
      $.ajax({url: ctxUrl + '/sys_menu/menu_info', method: 'post', data: {menuId: nowMenuId}}),
      $.ajax({url: ctxUrl + '/assets/lib/font-awesome/awesome_name.json'})
      )
      .done(function ([menuData], [icoList]) {
        const $parentId = $("input[name='parentId']")
          , $menuId = $("input[name='menuId']")
          , $menuName = $("input[name='menuName']")
          , $orderNum = $("input[name='orderNum']")
          , $icon = $("input[name='icon']")
          , $remark = $("input[name='remark']")
          , $icoList = utils.generateIcoListPro(icoList, callBack)
          , $menuBox = $(".form-group .menu-type-radio")
          , $formRadioChange = $('form .radio-change')
        $parentId.attr("value", menuData.data['parentName'])
        $parentId.attr("data-value", menuData.data['parentId'])
        $icon.after($icoList.dom)
        $icon.click($icoList.toggle)
        function callBack(name) {$icon.attr("value", name)}
        $menuId.val(menuData.data['menuId'])
        $menuName.val(menuData.data['menuName'])
        $orderNum.val(menuData.data['orderNum'])
        $icon.attr("value", menuData.data['icon'])
        $remark.val(menuData.data['remark'])
        $parentId.click(function () {
          layer.open({
            title: "菜单选择", type: 2, shade: 0.2,
            maxmin: true, move: false, skin: 'self-class',
            shadeClose: true, area: ['380px', '500px'],
            content: ctxUrl + '/sys_menu/menu_tree/#/id=' + menuData.data['menuId'],
            btn: ['确认'],
            success(_, index) {
              const body = layer.getChildFrame('body', index)//建立父子联系【核心语句】
                , sonInput = body.find('input[name="menuId"]')
              sonInput.attr("value", $parentId.attr("value"))
              sonInput.attr("data-value", $parentId.attr("data-value"))
            },
            yes(index, _) {
              const body = layer.getChildFrame('body', index)//建立父子联系【核心语句】
                , sonInput = body.find('input[name="menuId"]')
              $parentId.attr("value", sonInput.attr("value"))
              $parentId.attr("data-value", sonInput.attr("data-value"))
              layer.close(index)
            }
          })
        })
        if ('C' === menuData.data['menuType']) {
          $menuBox.append('<input type="radio" name="menuType" value="M" title="目录" />')
          $menuBox.append('<input type="radio" name="menuType" value="C" checked title="菜单" />')
          radioChange('C', $formRadioChange, menuData.data)
        } else {
          $menuBox.append('<input type="radio" name="menuType" value="M" checked title="目录" />')
          $menuBox.append('<input type="radio" name="menuType" value="C" title="菜单" />')
          radioChange('M', $formRadioChange, menuData.data)
        }
        form.render('radio', 'menuEditForm')
        form.on('radio', function ({value}) {
          radioChange(value, $formRadioChange, menuData.data)
        })
        form.on('submit(update-submit)', function ({field}) {
          field['parentId'] = $parentId.attr('data-value')
          field['path'] ??= '#'
          field['isFrame'] ??= '1'
          field['visible'] ??= '1'
          $.ajax({url: ctxUrl + '/sys_menu/update_menu', method: 'post', data: field})
            .done(({code, data, message}) => {
              layer.msg(data, {time: 1000}, function () {
                parent.layer.close(parent.layer.getFrameIndex(window.name))
                parent.location.reload()
              })
            })
          return false
        })
      })
      .fail(function () { window.location.reload()})
      .always(function () {layer.close(loadId)})

    $("#dictReply").on('click', () => { parent.layer.close(parent.layer.getFrameIndex(window.name))})
    /**
     * 单选切换事件函数
     */
    function radioChange(type, $dom, data) {
      $dom.html('')
      if ('C' === type) {
        $dom.append(`<div class="form-group"><label class="layui-col-md3 label_right required">请求地址：</label>
        <div class="layui-col-md7"><input type="text" class="layui-input" value="${ data['path'] }" lay-verify="required" name="path" placeholder="输入项目路径或者合法地址"></div></div>`)
        $dom.append(`<div class="form-group"><label class="layui-col-md3 label_right required">打开方式：</label>
        <div class="layui-col-md7"><select name="isFrame">
<option value="0" ${ data['isFrame'] === '0' ? 'selected' : '' }>新窗口</option>
<option value="1" ${ data['isFrame'] === '1' ? 'selected' : '' }>内页签</option>
</select></div></div>`)
      }
      form.render(null, 'menuEditForm')
    }
  })
})()