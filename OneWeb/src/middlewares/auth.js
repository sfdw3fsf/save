export default function auth ({ next, store }) {
  if (!store.getters['user/isAuthenticated']) {
    return next({
      name: 'login'
    })
  }
  return next()
}
