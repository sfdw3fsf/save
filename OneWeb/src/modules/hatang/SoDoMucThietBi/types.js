/**
 * @typedef {Object} IDCItem
 * @property {number} ID
 * @property {string} TEN
 */

/**
 * @typedef {Object} ThietBiItem
 * @property {number} ID
 * @property {number} KIEU
 * @property {string} TENTHIETBI
 */

/**
 * @typedef {Object} ContainerItem
 * @property {number} ID
 * @property {number} KIEU
 * @property {string} TEN
 */

/**
 * @typedef {Object.<number, number[]>} ViewTypeConfig
 */

/**
 * @typedef {Object} DiagramNode
 * @property {string} id
 * @property {number} type
 * @property {'container' | 'thietbi'} type_node
 * @property {number} width
 * @property {number} height
 * @property {number} offsetX
 * @property {number} offsetY
 * @property {Object} style
 * @property {string} style.fill
 * @property {string} style.strokeColor
 * @property {Array<Object>} annotations
 * @property {string} annotations[].content
 * @property {Object} annotations[].style
 * @property {string} annotations[].style.color
 * @property {boolean} annotations[].style.bold
 * @property {number} [annotations[].style.fontSize]
 * @property {Object} annotations[].offset
 * @property {number} annotations[].offset.x
 * @property {number} annotations[].offset.y
 * @property {Object} [shape]
 * @property {string} [shape.type]
 * @property {string} [shape.shape]
 */

export const Types = {};
