import Vue from 'vue'
import Router from 'vue-router'


Vue.use(Router)

function route (path, file, name, children) {
  return {
    exact: true,
    path,
    name,
    children,
    component: () => import('../pages' + file)
  }
}

export default new Router({
  routes: [
    route("/login",'/Login',"Login"),// /login路径，路由到登录组件
    {
      path:"/", // 根路径，路由到 Layout组件
      component: () => import('../pages/Layout'),
      redirect:"/item/stock",
      children:[ // 其它所有组件都是 Layout的子组件
        route("/item/stock",'/item/stock',"Stock"),
        route("/item/stock_price",'/item/stock_price',"stock_price"),
        route("/item/stock_times",'/item/stock_times',"stock_times"),
        route("/item/stock_chart",'/item/stock_chart',"stock_chart"),
      ]
    }
  ]
})
