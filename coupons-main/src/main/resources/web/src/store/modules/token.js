const token = {
  state: {
    tokenString: ''
  },

  mutations: {
    SET_TOKEN: (state, token) => {
      state.tokenString = token
    }
  }
}

export default token
