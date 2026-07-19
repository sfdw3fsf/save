import { PORT_TYPE } from '../const.js'

/**
 * Configuration for card module functionality
 */
export const CARD_MODULE_CONFIG = {
  // Button text configuration based on port type
  BUTTON_TEXT: {
    [PORT_TYPE.INTERFACE.NAME]: 'Thêm linecard',
    [PORT_TYPE.POWER.NAME]: 'Thêm power module', 
    [PORT_TYPE.CONSOLE.NAME]: 'Thêm console card',
    DEFAULT: 'Ghi lại'
  },
  
  // Title configuration for card module mode
  TITLE_CONFIG: {
    [PORT_TYPE.INTERFACE.NAME]: 'Thêm Interface Card',
    [PORT_TYPE.POWER.NAME]: 'Thêm Power Module',
    [PORT_TYPE.CONSOLE.NAME]: 'Thêm Console Card',
    DEFAULT: 'Thêm Card/Module'
  },
  
  // Default values for different card types
  DEFAULT_VALUES: {
    [PORT_TYPE.INTERFACE.NAME]: {
      is_interface: 1,
      is_power: null,
      is_console: null
    },
    [PORT_TYPE.POWER.NAME]: {
      is_interface: null,
      is_power: 1,
      is_console: null
    },
    [PORT_TYPE.CONSOLE.NAME]: {
      is_interface: null,
      is_power: null,
      is_console: 1
    }
  }
}

/**
 * Get button text based on current mode and card module type
 * @param {boolean} isCardModuleMode - Whether in card module mode
 * @param {number} portType - Port type ID
 * @param {string} currentMode - Current mode (VIEW, ADD, EDIT)
 * @returns {string} Button text
 */
export function getButtonText(isCardModuleMode, portType, currentMode) {
  if (currentMode === 'VIEW') {
    return 'Ghi lại'
  }
  
  if (isCardModuleMode && portType) {
    return CARD_MODULE_CONFIG.BUTTON_TEXT[portType] || CARD_MODULE_CONFIG.BUTTON_TEXT.DEFAULT
  }
  
  return CARD_MODULE_CONFIG.BUTTON_TEXT.DEFAULT
}

/**
 * Get title text for card module mode
 * @param {number} portType - Port type ID
 * @returns {string} Title text
 */
export function getCardModuleTitle(portType) {
  return CARD_MODULE_CONFIG.TITLE_CONFIG[portType] || CARD_MODULE_CONFIG.TITLE_CONFIG.DEFAULT
}

/**
 * Get default values for card type
 * @param {number} portType - Port type ID
 * @returns {object} Default values object
 */
export function getCardModuleDefaults(portType) {
  return CARD_MODULE_CONFIG.DEFAULT_VALUES[portType] || {}
}
