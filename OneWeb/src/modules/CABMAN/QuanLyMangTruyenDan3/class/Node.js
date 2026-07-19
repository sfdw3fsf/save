export default function Node() {
  this.id = null;
  this.id2 = null;
  this.Group = null;
  this.ImageKey = null;
  this.Label = null;
  this.Tooltip = null;
  this.Tag = null;
  this.MouseOver = false;
  this.IsDiemuon = false;
  this.Modified = false;
  this.Id_Route = null;
  this.Size = {
    width: 30,
    height: 30
  };
  this.Position = {
    lat: null,
    lng: null
  };
  this.AllowMove = true;
  this.Visible = true;
  this.RootId = null;
  this.Icon = null;
  this.HoverIcon = null;
  this.GetLabel = () => {
    return {
      text: this.Label,
      color: "black",
      fontSize: "12px",
      fontFamily: "Arial",
      fontWeight: "bold"
    };
  };
  this.GetIcon = () => {
    if (this.MouseOver) {
      return this.HoverIcon;
    } else {
      return this.Icon;
    }
  };
  this.SetIcon = ImageKey => {
    if (ImageKey === "DIEMUON") {
      const icondiemuon = {
        path: "M-20,0a20,20 0 1,0 40,0a20,20 0 1,0 -40,0",
        fillColor: "#F50606",
        fillOpacity: 0.6,
        anchor: new window.google.maps.Point(0, 0),
        strokeWeight: 0,
        scale: 0.4
      };
      this.Icon = icondiemuon;
      return;
    }

    let Icon = {
      url: "/static/icons/icon100x100/" + ImageKey + ".jpg",
      size: new window.google.maps.Size(this.Size.width, this.Size.height),
      scaledSize: new window.google.maps.Size(
        this.Size.width,
        this.Size.height
      ),
      labelOrigin: { x: 16, y: 40 },
      anchor: new window.google.maps.Point(15, 15)
    };
    let HoverIcon = {
      url: "/static/icons/icon100x100/" + ImageKey + "_sel.jpg",
      size: new window.google.maps.Size(this.Size.width, this.Size.height),
      scaledSize: new window.google.maps.Size(
        this.Size.width,
        this.Size.height
      ),
      labelOrigin: { x: 16, y: 40 },
      anchor: new window.google.maps.Point(15, 15)
    };
    this.Icon = Icon;
    this.HoverIcon = HoverIcon;
  };
}
