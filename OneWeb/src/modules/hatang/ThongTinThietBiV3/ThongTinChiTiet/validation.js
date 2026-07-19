/**
 * Lớp quản lý validation chính, điều phối tất cả các kiểm tra validation
 */
import moment from 'moment'
export class ValidationManager {
  constructor(validationRules, controls, getValueForControl) {
    this.validationRules = validationRules
    this.controls = controls
    this.getValueForControl = getValueForControl
  }

  /**
   * Phương thức validation chính, điều phối tất cả các kiểm tra validation
   * @returns {string} Thông báo lỗi validation (rỗng nếu hợp lệ)
   */
  validateControls() {
    this.resetValidation()
    const validationResults = this.runValidations()
    return this.formatValidationMessage(validationResults)
  }

  /**
   * Chạy tất cả các kiểm tra validation và trả về kết quả
   * @returns {Array} Mảng các kết quả validation
   */
  runValidations() {
    const results = []
    Object.keys(this.validationRules).forEach(controlKey => {
      const rule = this.validationRules[controlKey]
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
   * Kiểm tra xem một control có nên được validate dựa trên điều kiện của nó
   * @param {Object} rule - Rule validation
   * @returns {boolean}
   */
  shouldValidateControl (rule) {
    if (typeof rule.isValid === 'function') return true

    if (rule.condition && typeof rule.condition === 'function') {
      return rule.condition()
    }
    return rule.required
  }

  /**
   * Validate một control đơn lẻ
   * @param {string} controlKey - Key của control
   * @param {Object} rule - Rule validation
   * @returns {Object|null} Kết quả validation hoặc null nếu hợp lệ
   */
  validateControl(controlKey, rule) {
    const control = this.controls[controlKey]
    const value = this.getValueForControl(controlKey)

    let isValid = true

    // Nếu rule có isValid() → gọi isValid(value, context)
    if (typeof rule.isValid === 'function') {
      isValid = rule.isValid(value, {
        vm: this.vm,         
        controlKey           
      })
    }
    // Nếu rule có validator() → gọi validator(value)
    else if (typeof rule.validator === 'function') {
      isValid = rule.validator(value)
    }

    if (!isValid) {
      control.invalid = true
      const errorMessage = rule.getLastErrorMessage
        ? rule.getLastErrorMessage()
        : rule.message

      return {
        control: controlKey,
        message: errorMessage,
        value
      }
    }

    return null
  }


  /**
   * Định dạng kết quả validation thành một thông báo duy nhất
   * @param {Array} results - Mảng kết quả validation
   * @returns {string} Thông báo lỗi
   */
  formatValidationMessage(results) {
    if (results.length === 0) {
      return ''
    }
    return results.map(result => result.message).join('\n')
  }

  /**
   * Reset tất cả trạng thái validation về false
   */
  resetValidation() {
    Object.keys(this.controls).forEach((key) => {
      if (this.controls[key].hasOwnProperty('invalid')) {
        this.controls[key].invalid = false
      }
    })
  }

  /**
   * Bật hoặc tắt tất cả các controls
   * @param {boolean} enabled - Trạng thái enabled
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
 * Registry quản lý các rule creators
 * Cho phép đăng ký và sử dụng các rule creators một cách linh hoạt
 */
class ValidationRuleRegistry {
  constructor() {
    this.creators = new Map()
    this.registerDefaultRules()
  }

  registerDefaultRules() {
    this.register('required', (message, options) => 
      ValidationRulesFactory.createRequiredRule(message, options.validator)
    )
    this.register('text', (message) => 
      ValidationRulesFactory.createTextRule(message)
    )
    this.register('select', (message) => 
      ValidationRulesFactory.createSelectRule(message)
    )
    this.register('number', (message) => 
      ValidationRulesFactory.createNumberRule(message)
    )
    this.register('positiveNumber', (message) => 
      ValidationRulesFactory.createPositiveNumberRule(message)
    )
    this.register('integer', (message) => 
      ValidationRulesFactory.createIntegerRule(message)
    )
    this.register('numberRange', (message, options) => 
      ValidationRulesFactory.createNumberRangeRule(message, options.min, options.max)
    )
    this.register('conditional', (message, options) => 
      ValidationRulesFactory.createConditionalRule(message, options.condition, options.validator)
    )
    this.register('conditionalNumber', (message, options) => 
      ValidationRulesFactory.createConditionalNumberRule(message, options.condition)
    )
  }

  /**
   * Đăng ký một rule creator mới
   * @param {string} type - Tên của rule type
   * @param {Function} creator - Function tạo rule: (message, options) => rule object
   */
  register(type, creator) {
    if (typeof creator !== 'function') {
      console.warn(`Invalid creator function for rule type: ${type}`)
      return
    }
    this.creators.set(type, creator)
  }

  /**
   * Lấy rule creator theo type
   * @param {string} type - Tên của rule type
   * @returns {Function|null} Creator function hoặc null nếu không tìm thấy
   */
  get(type) {
    return this.creators.get(type) || null
  }

  /**
   * Kiểm tra xem rule type có tồn tại không
   * @param {string} type - Tên của rule type
   * @returns {boolean}
   */
  has(type) {
    return this.creators.has(type)
  }

  /**
   * Lấy danh sách tất cả các rule types đã đăng ký
   * @returns {Array<string>}
   */
  getAllTypes() {
    return Array.from(this.creators.keys())
  }
}

const ruleRegistry = new ValidationRuleRegistry()

/**
 * Factory tạo các validation rules có thể tùy chỉnh cho từng component
 */
export class ValidationRulesFactory {
  /**
   * Đăng ký một rule creator mới để có thể sử dụng trong createRule
   * @param {string} type - Tên của rule type
   * @param {Function} creator - Function tạo rule: (message, options) => rule object
   */
  static registerRule(type, creator) {
    ruleRegistry.register(type, creator)
  }

  /**
   * Lấy danh sách tất cả các rule types đã đăng ký
   * @returns {Array<string>}
   */
  static getRegisteredRuleTypes() {
    return ruleRegistry.getAllTypes()
  }

  /**
   * Tạo validation rule theo type(s)
   * @param {string|Array<string>|Object} typeOrRules - Type(s) của rule hoặc object mapping rule types với messages
   *   - string: Single rule type ('required', 'text', 'select', 'number', etc.)
   *   - Array<string>: Multiple rule types
   *   - Object: Mapping của rule types với messages, ví dụ: { required: 'msg', number: 'msg' }
   * @param {string|Array<string>|Object} message - Thông báo lỗi (chỉ dùng khi tham số đầu là string/array)
   * @param {Object} options - Tùy chọn bổ sung (condition function cho conditional rules, min/max cho range, custom validator)
   * @returns {Object} Validation rule object
   */
  static createRule(typeOrRules, message, options = {}) {
    if (typeof typeOrRules === 'object' && !Array.isArray(typeOrRules) && typeOrRules !== null) {
      const types = Object.keys(typeOrRules)
      const messages = typeOrRules
      return this.createCombinedRule(types, messages, options)
    }

    if (Array.isArray(typeOrRules)) {
      return this.createCombinedRule(typeOrRules, message, options)
    }

    const ruleCreator = ruleRegistry.get(typeOrRules)
    if (!ruleCreator) {
      console.warn(`Unknown validation rule type: ${typeOrRules}. Using required rule as fallback.`)
      return this.createRequiredRule(message, options.validator)
    }

    return ruleCreator(message, options)
  }

  /**
   * Tạo combined validation rule từ nhiều rule types
   * @param {Array<string>} types - Mảng các rule types
   * @param {string|Array<string>|Object} messages - Messages cho từng rule
   * @param {Object} options - Tùy chọn bổ sung
   * @returns {Object} Combined validation rule object
   */
  static createCombinedRule(types, messages, options = {}) {
    const rules = []
    const validators = []
    let combinedRequired = false

    let messageMap = {}
    if (typeof messages === 'string') {
      types.forEach(type => {
        messageMap[type] = messages
      })
    } else if (Array.isArray(messages)) {
      types.forEach((type, index) => {
        messageMap[type] = messages[index] || messages[0] || 'Validation failed'
      })
    } else if (typeof messages === 'object') {
      messageMap = messages
    }

    types.forEach((type, index) => {
      const ruleMessage = messageMap[type] || messageMap[types[0]] || 'Validation failed'
      
      const ruleCreator = ruleRegistry.get(type)
      if (!ruleCreator) {
        console.warn(`Unknown validation rule type: ${type}. Skipping this rule.`)
        return
      }

      const singleRule = ruleCreator(ruleMessage, options)
      if (!singleRule) {
        console.warn(`Failed to create rule for type: ${type}`)
        return
      }

      rules.push({ ...singleRule, type })
      validators.push({ validator: singleRule.validator, message: ruleMessage })
      if (singleRule.required) {
        combinedRequired = true
      }
    })

    let lastErrorMessage = null
    const combinedValidator = (value) => {
      for (const { validator, message } of validators) {
        if (!validator(value)) {
          lastErrorMessage = message
          return false
        }
      }
      lastErrorMessage = null
      return true
    }

    const hasCondition = rules.some(r => r.condition)
    const combinedCondition = hasCondition ? options.condition : undefined

    let mainMessage
    if (typeof messages === 'string') {
      mainMessage = messages
    } else if (Array.isArray(messages) && messages.length > 0) {
      mainMessage = messages[0]
    } else if (typeof messages === 'object' && Object.keys(messages).length > 0) {
      mainMessage = messages[types[0]] || Object.values(messages)[0]
    } else {
      mainMessage = 'Validation failed'
    }

    const combinedRule = {
      required: combinedRequired,
      validator: combinedValidator,
      message: mainMessage,
      condition: combinedCondition,
      _rules: rules,
      getLastErrorMessage: () => lastErrorMessage || mainMessage
    }

    return combinedRule
  }

  /**
   * Tạo rule validation bắt buộc có giá trị
   * @param {string} message - Thông báo lỗi
   * @param {Function} validator - Function kiểm tra (mặc định: kiểm tra có giá trị)
   * @returns {Object} Validation rule object
   */
  static createRequiredRule(message, validator = (value) => !!value) {
    return {
      required: true,
      validator,
      message
    }
  }

  /**
   * Tạo rule validation có điều kiện
   * @param {string} message - Thông báo lỗi
   * @param {Function} condition - Function kiểm tra điều kiện
   * @param {Function} validator - Function kiểm tra (mặc định: kiểm tra có giá trị)
   * @returns {Object} Validation rule object
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
   * Tạo rule validation cho text (chuỗi không rỗng)
   * @param {string} message - Thông báo lỗi
   * @returns {Object} Validation rule object
   */
  static createTextRule(message) {
    return {
      required: true,
      validator: (value) => value && value.trim() !== '',
      message
    }
  }

  /**
   * Tạo rule validation cho select (phải có giá trị được chọn)
   * @param {string} message - Thông báo lỗi
   * @returns {Object} Validation rule object
   */
  static createSelectRule(message) {
    return {
      required: true,
      validator: (value) => !!value,
      message
    }
  }

  /**
   * Tạo rule validation cho number (số hợp lệ)
   * @param {string} message - Thông báo lỗi
   * @returns {Object} Validation rule object
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
   * Tạo rule validation cho positive number (số dương)
   * @param {string} message - Thông báo lỗi
   * @returns {Object} Validation rule object
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
   * Tạo rule validation cho integer (số nguyên)
   * @param {string} message - Thông báo lỗi
   * @returns {Object} Validation rule object
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
   * Tạo rule validation cho number range (số trong khoảng)
   * @param {string} message - Thông báo lỗi
   * @param {number} min - Giá trị tối thiểu
   * @param {number} max - Giá trị tối đa
   * @returns {Object} Validation rule object
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
   * Tạo rule validation cho conditional number (số có điều kiện)
   * @param {string} message - Thông báo lỗi
   * @param {Function} condition - Function kiểm tra điều kiện
   * @returns {Object} Validation rule object
   */
  static createConditionalNumberRule(message, condition) {
    return {
      required: false,
      validator: (value) => {
        if (value === null || value === undefined || value === '') return true
        const num = Number(value)
        return !isNaN(num) && isFinite(num)
      },
      message,
      condition
    }
  }

  /**
   *  Tạo rule cho date picker (ngày hợp lệ hoặc nullable)
   *  @param {string} message - Thông báo lỗi
   *  @returns {Object} Validation rule object
   */
  static createDatePickerRule(message) {
    return {
      required: false,          // ngày có thể nullable
      message,
      isValid: (value, context) => {
        const controlKey = context.controlKey

        const ref = context.vm.$refs[`${controlKey}_picker`]
        if (!ref || !ref.ej2Instances) return true  

        const inputEl = ref.ej2Instances.element
        const text = inputEl.value.trim()

        if (!text) {
          inputEl.classList.remove("invalid-date")
          return true
        }

        const date = moment(text, "DD/MM/YYYY", true)
        const valid = date.isValid() && date.year() > 1900 && date.year() < 2099

        if (!valid) {
          inputEl.classList.add("invalid-date")
          return false
        }

        inputEl.classList.remove("invalid-date")
        return true
      },
      requiredMessage: null
    }
  }

}

/**
 * Mixin cho các component cần chức năng validation
 * Có thể import và sử dụng trong Vue components
 */
export const validationMixin = {
  methods: {
    /**
     * Khởi tạo validation manager
     * @returns {ValidationManager|null} Validation manager hoặc null nếu thiếu dependencies
     */
    initValidation() {
      if (!this.validationRules || !this.controls || !this.getValueForControl) {
        console.warn('Validation mixin requires validationRules, controls, and getValueForControl to be defined')
        return null
      }
      
      const manager = new ValidationManager(
        this.validationRules,
        this.controls,
        this.getValueForControl.bind(this)
      )
      manager.vm = this
      return manager
    },

    /**
     * Validate các controls sử dụng validation manager
     * @returns {string} Thông báo lỗi validation (rỗng nếu hợp lệ)
     */
    validateControls() {
      const validationManager = this.initValidation()
      if (!validationManager) return ''
      
      return validationManager.validateControls()
    },

    /**
     * Reset trạng thái validation
     */
    resetValidation() {
      const validationManager = this.initValidation()
      if (!validationManager) return
      
      validationManager.resetValidation()
    },

    /**
     * Bật hoặc tắt các controls
     * @param {boolean} enabled - Trạng thái enabled
     */
    enableControls(enabled) {
      const validationManager = this.initValidation()
      if (!validationManager) return
      
      validationManager.enableControls(enabled)
    }
  }
}
