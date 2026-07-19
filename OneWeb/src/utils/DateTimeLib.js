import moment from "moment";
export default {
  toDateDisplay: function(dateObject){
    return this.toDateDisplayWithFormat(dateObject, "DD/MM/YYYY");
  },
  toDateDisplayWithFormat: function(dateObject, format){
    return this.isNullOrEmpty(dateObject) ? null : moment(dateObject).format(format);
  },
  toDateDisplayStandar: function(currentStringDate, currentFormat){
    return this.toDateDisplay(this.toDateObjectWithFormat(currentStringDate, currentFormat));
  },
  toDateTimeDisplay: function(dateTimeObject){
    return this.toDateTimeDisplayWithFormat(dateTimeObject, "DD/MM/YYYY HH:mm");
  },
  toDateTimeDisplayWithFormat: function(dateTimeObject, format){
    return this.isNullOrEmpty(dateTimeObject) ? null : moment(dateTimeObject).format(format);
  },
  toDateTimeDisplayStandar: function(currentStringDate, currentFormat){
    return this.toDateTimeDisplay(this.toDateObjectWithFormat(currentStringDate, currentFormat));
  },
  toDateObject: function(stringDate){
    return this.toDateObjectWithFormat(stringDate, "DD/MM/YYYY");
  },
  toDateObjectWithFormat: function(stringDate, format){
    return this.isNullOrEmpty(stringDate) ? null : moment(stringDate, format).toDate();
  },
  toDateTimeObject: function(stringDateTime){
    return this.toDateTimeObjectWithFormat(stringDateTime, "DD/MM/YYYY HH:mm");
  },
  toDateTimeObjectWithFormat: function(stringDateTime, format){
    return this.isNullOrEmpty(stringDateTime) ? null : moment(stringDateTime, format).toDate();
  },
  isNullOrEmpty: function (value) {
    return (
      value == undefined || value == null || value.toString().trim() == ""
    );
  },
}
