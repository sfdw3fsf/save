/**
 * @summary:
 * Watcher for keydown events
 * Usage (view more details on /modules/search/subscriber/SearchSubcriberIMS):
 *
 *  component:
 *   created() {
 *      // 'this' is this component's handle, 'window' is the main window handle
 *      this.kw = new PopupKeyWatcher(this, window)
 *      // register mainform's keydown event
 *      this.kw.register(null, this.onF3Pressed)
 *   },
 *   destroyed() {
 *      // clear all registered
 *      if (this.kw) this.kw.clear()
 *   },
 *  component's methods: (ex: my-modal)
 *   showModal() {
 *     if (!this.$refs['my-modal'].visible) {
 *       this.$refs['my-modal'].show()
 *       this.kw.register(this.$refs['my-modal'], this.onF3SearchPressed)
 *     }
 *   },
 *   onF3Pressed(e) {
 *     if (e.keyCode == 114) {
 *       this.showModal()
 *       e.preventDefault()
 *     }
 *   },
 *   onF3SearchPressed(e) {
 *     if (e.keyCode == 114) {
 *       // do something here
 *       e.preventDefault()
 *     }
 *   }
 */
export default class PopupKeyWatcher {
  eventList = []
  popupList = []
  wnd = null
  component = null
  mainEvt = null

  constructor(component, window) {
    this.wnd = window
    this.component = component
    this.watch()
  }

  // private
  watch() {
    this.component.$root.$on('bv::modal::hide', (bvEvent, modalId) => {
      this.removeEvents()
      // console.log('Modal is about to be hidden', bvEvent, modalId)
      try {
        const idx = this.popupList.findIndex((x) => x.localId_ == modalId)
        // console.log('idx = ' + idx)
        // console.log(this.popupList)
        if (idx <= 0) this.registerMain()
        else {
          const evt = this.eventList[idx - 1]
          // un-register this popup key event
          this.wnd.addEventListener('keydown', evt)
          this.popupList.splice(idx, 1)
          this.eventList.splice(idx, 1)
        }
      } catch (error) {
        this.removeEvents()
        this.registerMain()
      }
    })
  }

  // private
  registerMain() {
    if (null != this.mainEvt) this.wnd.addEventListener('keydown', this.mainEvt)
  }

  // private
  removeEvents() {
    if (this.mainEvt != null) {
      window.removeEventListener('keydown', this.mainEvt)
    }
    for (const evt of this.eventList) {
      this.wnd.removeEventListener('keydown', evt)
    }
  }

  // public
  register(popup, keydownEvt) {
    this.removeEvents()
    if (null == popup) {
      // this is main form's event
      this.mainEvt = keydownEvt
      this.wnd.addEventListener('keydown', keydownEvt)
    } else {
      this.wnd.addEventListener('keydown', keydownEvt)
      this.popupList.push(popup)
      this.eventList.push(keydownEvt)
    }
  }

  // public
  clear() {
    // clear all registered
    this.removeEvents()
    this.eventList = []
    this.popupList = []
    this.mainEvt = null
  }
}
