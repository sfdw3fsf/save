'use strict'
const merge = require('webpack-merge')
const prodEnv = require('./prod.env')

module.exports = merge(prodEnv, {
  NODE_ENV: '"development"',
  // API: '"https://api-onebss.vnpt.vn"'
  API: '"https://api-dev-onebss.vnpt.vn"'
})
