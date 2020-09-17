import React, { Component } from "react";
import { connect } from "react-redux";
import store from "./store";
import Axios from "axios";
class MyForm extends Component {
  constructor(props) {
    super(props);
    this.state = {};
  }
  render() {
    return (
      <div>
        <input value={this.props.name} />
        <button onClick={this.props.clickBTN}>sec kill</button>
      </div>
    );
  }
}
const stateToProps = (state) => {
  return {
    name: state.name,
  };
};
const dispatchToProps = (dispatch) => {
  return {
    clickBTN(e) {
      console.log(e);
      Axios.get("http://localhost:8080/sec/test")
        .then((res) => {
          console.log(res);
          if (res.data.msg === "yes") {
            dispatch({ type: "success" });
          } else {
            dispatch({ type: "fail" });
          }
        })
        .catch((error) => {
          console.log(error);
        });
    },
  };
};
export default connect(stateToProps, dispatchToProps)(MyForm);
