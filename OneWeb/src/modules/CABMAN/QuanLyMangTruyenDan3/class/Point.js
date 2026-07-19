export default function Point () {
  this.id = null
  this.annotations = []
  this.Group = null
  this.ImageKey = null
  this.Name = null
  this.DisplayName = null
  this.Position = {
    x: 0,
    y: 0
  }
  this.Size = {
    width: null,
    height: null
  }
  this.AllowMove = false
  this.RootId = null
}
