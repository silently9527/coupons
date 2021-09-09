const token = {
  state: {
    init: false,
    tokenString: ''
  },

  mutations: {
    INIT: (state) => {
      state.init = true
    },
    SET_TOKEN: (state, token) => {
      state.tokenString = token
    }
  }
}

export default token
