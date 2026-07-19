export default function Route() {
  this.A = null;
  this.B = null;
  this.id = null;
  this.id2 = null;
  this.Group = null;
  this.Name = null;
  this.Tag = null;
  this.Layer = null;
  this.BackColor = null;
  this.Width = 2;
  this.RootId = null;
  this.Positions = [];
  this.MouseOver = false;
  this.Visible = true;
  this.Modified = false;
  this.ThemPosition = (id, position) => {
    var p = {
      id: id,
      lat: position.lat,
      lng: position.lng
    };
    this.Positions.push(p);
  };
  this.create_UUID = () => {
    var dt = new Date().getTime();
    var uuid = "xxxxxxxx_xxxx_4xxx_yxxx_xxxxxxxxxxxx".replace(/[xy]/g, function(
      c
    ) {
      var r = (dt + Math.random() * 16) % 16 | 0;
      dt = Math.floor(dt / 16);
      return (c === "x" ? r : (r & 0x3) | 0x8).toString(16);
    });
    return uuid;
  };
  this.GetPoints = () => {
    var day_toado = null;
    for (let index = 1; index < this.Positions.length - 1; index++) {
      if (day_toado === null) {
        day_toado = this.Positions[index].lat + "," + this.Positions[index].lng;
      } else {
        day_toado =
          day_toado +
          ";" +
          this.Positions[index].lat +
          "," +
          this.Positions[index].lng;
      }
    }
    return day_toado;
  };
  this.ThemDanhSachPosition = positions => {
    try {
      if (positions === null) return;
      if (positions.length > 0) {
        var arr = positions.split(";");
        arr.forEach(element => {
          var arr2 = element.split(",");
          var id = "DIEMUON_" + this.create_UUID();
          var position = {
            id: id,
            lat: parseFloat(arr2[0]),
            lng: parseFloat(arr2[1])
          };
          this.Positions.push(position);
        });
      }
    } catch (error) {
      console.log("ThemDanhSachPosition", error);
    }
  };
  this.RouteOptions = () => {
    try {
      var opt = { strokeOpacity: 1, geodesic: true };
      opt = {
        ...opt,
        strokeWeight: this.Width
      };
      opt = {
        ...opt,
        strokeColor: this.MouseOver ? "Blue" : "#" + this.BackColor
      };
      return opt;
    } catch (error) {
      console.log("RouteOptions", error);
    }
  };
}
