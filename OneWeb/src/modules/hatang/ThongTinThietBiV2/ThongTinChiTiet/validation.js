/**
 * Common validation utilities for device information components
 * This file contains reusable validation logic that can be shared across components
 */

/**
 * Main validation class that orchestrates all validation checks
 */
export class ValidationManager {
  constructor(validationRules, controls, getValueForControl) {
    this.validationRules = validationRules
    this.controls = controls
    this.getValueForControl = getValueForControl
  }

  /**
   * Main validation method that orchestrates all validation checks
   */
  validateControls() {
    this.resetValidation()
    const validationResults = this.runValidations()
    return this.formatValidationMessage(validationResults)
  }

  /**
   * Run all validation checks and return results
   */
  runValidations() {
    const results = []
    // Validate each control based on rules
    Object.keys(this.validationRules).forEach(controlKey => {
      const rule = this.validationRules[controlKey]
      // Check if this rule should be applied based on conditions
      if (this.shouldValidateControl(rule)) {
        const result = this.validateControl(controlKey, rule)
        if (result) {
          results.push(result)
        }
      }
    })
    
    return results
  }

  /**
   * Check if a control should be validated based on its conditions
   */
  shouldValidateControl(rule) {
    if (rule.condition && typeof rule.condition === 'function') {
      return rule.condition()
    }
    return rule.required
  }

  /**
   * Validate a single control
   */
  validateControl(controlKey, rule) {
    const control = this.controls[controlKey]
    const value = this.getValueForControl(controlKey)
    
    if (!rule.validator(value)) {
      control.invalid = true
      return {
        control: controlKey,
        message: rule.message,
        value: value
      }
    }
    
    return null
  }

  /**
   * Format validation results into a single message
   */
  formatValidationMessage(results) {
    if (results.length === 0) {
      return ''
    }
    
    return results.map(result => result.message).join('\n\r')
  }

  /**
   * Reset all validation states to false
   */
  resetValidation() {
    Object.keys(this.controls).forEach((key) => {
      if (this.controls[key].hasOwnProperty('invalid')) {
        this.controls[key].invalid = false
      }
    })
  }

  /**
   * Enable or disable all controls
   */
  enableControls(enabled) {
    Object.keys(this.controls).forEach((key) => {
      if (this.controls[key].hasOwnProperty('enabled')) {
        this.controls[key].enabled = enabled
      }
    })
  }
}

/**
 * Common validation rules factory
 * Creates validation rules that can be customized per component
 */
export class ValidationRulesFactory {
  /**
   * Create basic required field validation rule
   */
  static createRequiredRule(message, validator = (value) => !!value) {
    return {
      required: true,
      validator,
      message
    }
  }

  /**
   * Create conditional validation rule
   */
  static createConditionalRule(message, condition, validator = (value) => !!value) {
    return {
      required: false,
      validator,
      message,
      condition
    }
  }

  /**
   * Create text validation rule (non-empty string)
   */
  static createTextRule(message) {
    return {
      required: true,
      validator: (value) => value && value.trim() !== '',
      message
    }
  }

  /**
   * Create select validation rule (has value)
   */
  static createSelectRule(message) {
    return {
      required: true,
      validator: (value) => !!value,
      message
    }
  }

  /**
   * Create number validation rule (valid number)
   */
  static createNumberRule(message) {
    return {
      required: true,
      validator: (value) => {
        if (value === null || value === undefined || value === '') return false
        const num = Number(value)
        return !isNaN(num) && isFinite(num)
      },
      message
    }
  }

  /**
   * Create positive number validation rule (positive number)
   */
  static createPositiveNumberRule(message) {
    return {
      required: true,
      validator: (value) => {
        if (value === null || value === undefined || value === '') return false
        const num = Number(value)
        return !isNaN(num) && isFinite(num) && num > 0
      },
      message
    }
  }

  /**
   * Create integer validation rule (whole number)
   */
  static createIntegerRule(message) {
    return {
      required: true,
      validator: (value) => {
        if (value === null || value === undefined || value === '') return false
        const num = Number(value)
        return !isNaN(num) && isFinite(num) && Number.isInteger(num)
      },
      message
    }
  }

  /**
   * Create number range validation rule (number within range)
   */
  static createNumberRangeRule(message, min, max) {
    return {
      required: true,
      validator: (value) => {
        if (value === null || value === undefined || value === '') return false
        const num = Number(value)
        return !isNaN(num) && isFinite(num) && num >= min && num <= max
      },
      message
    }
  }

  /**
   * Create conditional number validation rule
   */
  static createConditionalNumberRule(message, condition) {
    return {
      required: false,
      validator: (value) => {
        if (value === null || value === undefined || value === '') return true // Allow empty for conditional
        const num = Number(value)
        return !isNaN(num) && isFinite(num)
      },
      message,
      condition
    }
  }
}

/**
 * Mixin for components that need validation functionality
 * This can be imported and used in Vue components
 */
export const validationMixin = {
  methods: {
    /**
     * Initialize validation manager
     */
    initValidation() {
      if (!this.validationRules || !this.controls || !this.getValueForControl) {
        console.warn('Validation mixin requires validationRules, controls, and getValueForControl to be defined')
        return null
      }
      
      return new ValidationManager(
        this.validationRules,
        this.controls,
        this.getValueForControl.bind(this)
      )
    },

    /**
     * Validate controls using the validation manager
     */
    validateControls() {
      const validationManager = this.initValidation()
      if (!validationManager) return ''
      
      return validationManager.validateControls()
    },

    /**
     * Reset validation states
     */
    resetValidation() {
      const validationManager = this.initValidation()
      if (!validationManager) return
      
      validationManager.resetValidation()
    },

    /**
     * Enable or disable controls
     */
    enableControls(enabled) {
      const validationManager = this.initValidation()
      if (!validationManager) return
      
      validationManager.enableControls(enabled)
    }
  }
}
