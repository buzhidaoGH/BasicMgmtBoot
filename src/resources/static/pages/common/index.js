;(function () {
  const element = layui.element,// tab选项卡切换,nav导航菜单的点击等..
    $ = layui.jquery,
    layer = layui.layer,
    $definedSide = $(".layui-side-scroll .defined-side"),
    $definedTab = $(".layui-body .layui-tab-title")
  let loading = utils.loadingFun('加载中...')

  $.when(
    $.ajax({url: ctxUrl + '/sys_user/user_info', dataType: 'json'}),
    $.ajax({url: ctxUrl + '/sys_dict_data/index_info', dataType: 'json'}),
    $.ajax({url: ctxUrl + '/sys_menu/menu_list', method: 'post', dataType: 'json'})
    )
    .done(function ([userInfo], [indexInfo], [menuList]) {
      const userInfoData = userInfo.data,
        indexInfoData = indexInfo.data,
        menuListData = menuList.data
      // 渲染dict的基本值
      $('.layui-header .layui-logo').text(indexInfoData['logoTitle']['dictValue'])
      document.title = indexInfoData['sysTitle']['dictValue']
      // 渲染用户信息
      $('.layui-header .layui-nav-item .layui-nav-img').attr('src', ctxUrl + userInfoData.avatar)
      $('.layui-header .layui-nav-item .defined-nav-name').text(userInfoData.nickName)
      $('.layui-side .user-avatar .userName').text(userInfoData.userName)
      $('.layui-side .user-avatar .user-img').attr('src', ctxUrl + userInfoData.avatar)
      // 侧边栏数据初始化渲染
      if (Array.isArray(menuListData)) {
        utils.arrayMenuSort({list: menuListData})
        $definedSide.append(utils.getSideHtml2(menuListData, true).data)
      }
      /*请求数据后重新渲染nav*/
      element.render('nav', 'defined-nav')
      /*侧边栏导航栏点击事件(动态tab栏)*/
      sideClick()
      /*默认触发第一个Tab,和是否产生hash地址位置Tab*/
      initTab()
      listTabRight()
      sideChange()
      /** 右边tab影响左边nav选中 */
      tabAffectNav()
      infoEvent(userInfoData)
    })
    .fail(function () { window.location.reload()})
    .always(function () {
      /** 所有事件初始成功后关闭loading层 */
      layer.close(loading)
    })

  /** 侧边栏导航栏点击事件(动态tab栏) */
  function sideClick() {
    $(".layui-side .defined-side").on("click", ".side-site-active", function (event) {
      const {menuId, path, target, title, menuType} = sideItemVal(this)
      if ('M' === menuType) {return}
      if (target) {
        utils.openNewWindow(path)
        return
      }
      if (tabIsExist(menuId)) {
        element.tabChange("defined-tab", menuId)
        return
      }
      // 完全新建则走下面
      element.tabAdd("defined-tab", {
        title: `<i class="fa fa-circle"></i>${ title }`,
        content: `<iframe width="100%" height="100%" lay-id="${ menuId }" frameborder="no" border="0" marginwidth="0" marginheight="0" src="${ ctxUrl + path }/#/title=${ title }"></iframe>`, //支持传入html
        id: menuId
      })
      element.tabChange("defined-tab", menuId)
    })
  }
  /** 默认触发第一个Tab,和是否产生hash地址位置Tab */
  function initTab() {
    const $firstItem = $definedSide.find(".layui-nav-item:first")
    if ($firstItem.find("dl").length > 0) {
      $firstItem.find("a:first").click()
      $firstItem.find("dl>dd>a:first").click()
    } else {
      $firstItem.find("a:first").click()
    }
  }
  /** tab栏是否存在相同的 */
  function tabIsExist(menuId) {
    try {
      $definedTab.children("li").each(function (index, elem) {
        if (menuId && menuId === $(elem).attr('lay-id')) throw('')
      })
    } catch (e) {
      return true
    }
    return false
  }
  /** 监听tab标题栏右键事件 */
  function listTabRight() {
    const $rightMenu = $('.layui-body .defined-tab-control'),
      $tabShade = $('.layui-body .defined-tab-shade')
    $definedTab.on('contextmenu', 'li', function (event) {
      const {left} = $(this).position(),
        widthTab = $(this).outerWidth()
      $rightMenu.css('left', (widthTab / 2 + left) + 'px')
      $rightMenu.attr('data-id', $(this).attr('lay-id'))
      $rightMenu.show()
      $tabShade.show()
      return false
    })
    $(document).on("click", function (event) {
      if (!$(event.target).closest($rightMenu).length) {
        $rightMenu.hide()
        $tabShade.hide()
      }
    })
    $rightMenu.on("click", "dd", function (event) {
      const type = $(this).attr('data-type'),
        menuId = $rightMenu.attr('data-id'),
        menuIdList = []
      $definedTab.children('li').each(function (index, elem) {
        menuIdList.push($(elem).attr("lay-id"))
      })
      if (type === 'current' && menuIdList[0] !== menuId) {
        element.tabDelete('defined-tab', menuId)
      } else if (type === 'other') {
        for (let i = 1; i < menuIdList.length; i++) {
          if (menuIdList[i] !== menuId) element.tabDelete('defined-tab', menuIdList[i])
        }
      } else if (type === 'all') {
        for (let i = 1; i < menuIdList.length; i++) {
          element.tabDelete('defined-tab', menuIdList[i])
        }
      } else if (type === 'refresh') {
        const targetIframe = $(`.layui-tab-content .layui-tab-item iframe[lay-id=${ menuId }]`)
        targetIframe[0].contentWindow.location.reload()
      }
      $rightMenu.hide()
      $tabShade.hide()
    })
  }
  /** 侧边栏收缩展开事件 */
  function sideChange() {
    const $headMenu = $('.layui-header .menu'),
      $sideCtx = $('.layui-layout-admin .layui-side'),
      $bodyCtx = $('.layui-layout-admin .layui-body'),
      $footCtx = $('.layui-layout-admin .layui-footer')
    $headMenu.click(function (event) {
      if ($(this).hasClass('narrow')) {
        $(this).removeClass('narrow')
        $sideCtx.css('left', '0')
        $bodyCtx.css('left', '200px')
        $footCtx.css('left', '200px')
      } else {
        $(this).addClass('narrow')
        $sideCtx.css('left', '-200px')
        $bodyCtx.css('left', '0')
        $footCtx.css('left', '0')
      }
    })
  }
  /** 侧边栏的menu值提取 */
  function sideItemVal(elem) {
    const res = {menuId: undefined, menuType: undefined, path: undefined, target: undefined, title: undefined}
    res.title = $(elem).text()
    res.menuId = $(elem).attr('data-id')
    res.path = $(elem).attr('data-path')
    res.target = $(elem).attr('data-target') === "0"
    res.menuType = $(elem).attr('data-type')
    return res
  }
  /** 右边tab切换影响左边nav选中 */
  function tabAffectNav() {
    element.on('tab(defined-tab)', function () {
      const layId = $(this).attr('lay-id'),
        $layDom = $definedSide.find(`a[data-id=${ layId }]`)
      $definedSide.find('.layui-this').removeClass('layui-this')
      $definedSide.find('.layui-nav-itemed').removeClass('layui-nav-itemed')
      let tagName = $layDom.parent().prop("tagName")
      $layDom.parent().addClass("layui-this")
      if (tagName === 'DD') {
        $layDom.parents("dd[class=layui-nav-item]").addClass("layui-nav-itemed")
      }
    })
  }
  /** 个人特殊事件 */
  function infoEvent(userInfo) {
    const $infoEvent = $('.layui-nav-child.info-event')
    $infoEvent.on('click', 'dd>a', function (event) {
      let layEvent = $(this).attr('lay-event')
      switch (layEvent) {
        case 'info':
          layer.open({
            title: "用户详情", type: 2, shade: 0.2,
            maxmin: true, move: false, skin: 'self-class',
            shadeClose: true, area: ['70%', '70%'],
            content: ctxUrl + '/sys_user/user_details/#/userId=' + userInfo.userId
          })
          break
        case 'pwd':
          layer.open({
            title: "更改密码", type: 1, shade: 0.2,
            move: false, skin: 'self-class',
            shadeClose: true, area: ['400px', '220px'],
            content: $('#pwdTep').html(),
            btn: ["更改", "取消"],
            yes(index, layero) {
              const $newPwd = layero.find('input[name="new_pwd"]')
                , $oldPwd = layero.find('input[name="old_pwd"]')
                , newPwdStr = $newPwd.val()
                , oldPwdStr = $oldPwd.val()
              if (isEmptyStr(newPwdStr) || isEmptyStr(oldPwdStr)) {
                layer.msg('新旧密码不可为空！', {icon: 2, time: 1000})
              } else {
                $.ajax({
                    url: ctxUrl + '/sys_user/change_pwd', method: 'post',
                    data: {userName: userInfo['userName'], oldPwd: oldPwdStr, newPwd: newPwdStr}
                  })
                  .done(({code, message}) => {
                    if (code === 0) {
                      layer.msg(message, {icon: 1, time: 1000}, () => {window.location.reload()})
                    } else {
                      layer.msg(message, {icon: 2, time: 1000})
                    }
                    layer.close(index)
                  })
              }
              return false
            }
          })
          break
        case 'logout':
          layer.confirm('确认退出登陆?', {icon: 3, title: '提示'},
            function (index) {
              $.ajax({
                  url: ctxUrl + '/sys_user/logout', method: 'post',
                  data: {userId: userInfo['userId'], userName: userInfo['userName']}
                })
                .done(({code, message}) => {
                  if (code === 0) {
                    layer.msg(message, {icon: 1, time: 1000}, () => {window.location.reload()})
                  } else {
                    layer.msg(message, {icon: 2, time: 1000})
                  }
                  layer.close(index)
                })
            }
          )
          break
      }

    })
    function isEmptyStr(s) {
      if (typeof s == 'string') {
        s = s.trim()
        return !(s.length > 0)
      }
      return true
    }
  }
})()



