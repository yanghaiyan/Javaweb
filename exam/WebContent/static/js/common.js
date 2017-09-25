  function isInteger(str) {
      var regu = /^[-]{0,1}[0-9]{1,}$/;
      return regu.test(str);
  }
  
  String.prototype.trim = function () {
      return this.replace(/(\s*$)/g, "");
  }
  String.prototype.ltrim = function () {
      return this.replace(/(^\s*)/g, "");
  }
  String.prototype.trim = function () {
      return this.replace(/(^\s*)|(\s*$)/g, "");
  }