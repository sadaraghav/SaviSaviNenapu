import React from 'react';

var Footer = React.createClass({
  render() {
    console.log("Footer Screen");
    var style = {
    marginLeft:"30%",
    color: '#E33054'
    };  
    return (
      <footer className="footer" id="footer">
        <div  style={style} className="copyright">
            {'Created By'} <a href="#">Sada </a> &amp; <a href="#">Raghav</a>  | <span className="js-now-year">2017</span>
        </div>
      </footer>
    );
  }
});

export default Footer;
