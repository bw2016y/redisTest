import React from "react";
import store from ".";
import axios from "axios";
const defaultState = {
  name: "bbb",
};

export default (state = defaultState, action) => {
  if (action.type === "success") {
    let newState = JSON.parse(JSON.stringify(state));
    newState.name = "lucky";
    return newState;
  }
  if (action.type === "fail") {
    let newState = JSON.parse(JSON.stringify(state));
    newState.name = "fail";
    return newState;
  }
  return state;
};
