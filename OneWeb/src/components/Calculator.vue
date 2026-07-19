<template>
    <div class="calc-card dropdown-menu">
        <div class="top">
            <div class="item space">{{ isMemoried }}</div>
            <div class="item back" @click="back()">Back</div>
            <div class="item ce" @click="clear()">CE</div>
            <div class="item c" @click="clear()">C</div>
        </div>
        <div class="list-calc">
            <div class="item red" @click="memoryStore('MC')">
                mc
            </div>
            <div class="item" @click="appendDigit(7)">
                7
            </div>
            <div class="item" @click="appendDigit(8)">
                8
            </div>
            <div class="item" @click="appendDigit(9)">
                9
            </div>
            <div class="item red" @click="handleOperator('/')">
                /
            </div>
            <div class="item red" @click="calculate('sqrt')">
                sqrt
            </div>
            <div class="item red" @click="memoryStore('MR')">
                MR
            </div>
            <div class="item" @click="appendDigit(4)">
                4
            </div>
            <div class="item" @click="appendDigit(5)">
                5
            </div>
            <div class="item" @click="appendDigit(6)">
                6
            </div>
            <div class="item red" @click="handleOperator('*')">
                *
            </div>
            <div class="item" @click="calculate('%')">
                %
            </div>
            <div class="item red" @click="memoryStore('MS')">
                MS
            </div>
            <div class="item" @click="appendDigit(1)">
                1
            </div>
            <div class="item" @click="appendDigit(2)">
                2
            </div>
            <div class="item" @click="appendDigit(3)">
                3
            </div>
            <div class="item red" @click="handleOperator('-')">
                -
            </div>
            <div class="item" @click="calculate('1/x')">
                1/x
            </div>
            <div class="item red" @click="memoryStore('M+')">
                M+
            </div>
            <div class="item" @click="appendDigit(0)">
                0
            </div>
            <div class="item" @click="calculate('+/-')">
                +/-
            </div>
            <div class="item" @click="dot()">
                .
            </div>
            <div class="item red" @click="handleOperator('+')">
                +
            </div>
            <div class="item" @click="handleOperator('=')">
                =
            </div>
        </div>
    </div>
</template>
<script>
export default {
    name: 'Calculator',
    props: ['value', 'isShow'],
    data: function () {
        return {      
            currentValue: 0,         
            savedValue: false,
            currentOperator: false,
            memoryValue: 0,
            isMemoried: '',
            decimalDigit: 0,
            isDot: false,
            clickCount: 0
        }
    },   
    beforeDestroy() {
        window.removeEventListener('click', this.checkClickOn)
    },    
    methods: {
        checkClickOn (event) {                
            if (this.clickCount !== 0 && this.$el.className.includes('show') && event.target instanceof HTMLElement && !this.$el.contains(event.target)) {                                                                        
                this.$emit('close')
            }         
            this.clickCount++                 
        },
        close () {         
            this.isDot = false   
            this.decimalDigit = 0
            this.currentOperator = false 
            this.savedValue = false
            this.isMemoried = ''
            this.clickCount = 0
            this.$el.classList.remove('show')            
            window.removeEventListener('click', this.checkClickOn)        
        },
        clear () {            
            this.currentValue = 0
            this.decimalDigit = 0
            this.isDot = false
            this.savedValue = false
            this.currentOperator = false            
            this.$emit('display', this.displayedValue)
        },
        back (digit) {   
            if (this.currentValue !== 0) {   
                if (!this.isDot) {
                    this.currentValue = Math.floor(this.displayedValue / 10)
                } else {
                    this.decimalDigit -= 1
                    if (this.decimalDigit === 0) {
                        this.isDot = false
                    }
                    let pow = Math.pow(10, this.decimalDigit)
                    this.currentValue = Math.floor(this.displayedValue * pow) / pow
                }
            }
            this.$emit('display', this.displayedValue)
        },      
        appendDigit (digit) {
            if (this.currentOperator === '=') {
                this.savedValue = false
            }                  

            if (this.isDot) {
                this.decimalDigit += 1
                this.currentValue += digit / Math.pow(10, this.decimalDigit)                
            } else {
                this.currentValue = this.currentValue * 10 + digit
            }
            this.$emit('display', this.displayedValue)
        },
        handleOperator (operator) {
            this.decimalDigit = 0        
            this.isDot = false    
            if (this.currentOperator) {
                if (this.currentOperator === '+') {
                    this.savedValue += this.currentValue
                }
                else if (this.currentOperator === '-') {
                    this.savedValue -= this.currentValue
                }
                else if (this.currentOperator === '*') {
                    this.savedValue *= this.currentValue
                }
                else if (this.currentOperator === '/') {
                    this.savedValue /= this.currentValue
                }                
                else if (this.currentOperator === '=' && this.currentValue) {
                    this.savedValue = this.currentValue
                }
                this.currentValue = 0
                this.currentOperator = false
            }
            else {
                this.savedValue = this.currentValue
            }
            this.currentValue = 0
            this.currentOperator = operator
            this.$emit('display', this.displayedValue)
        },
        calculate (type) {
            this.decimalDigit = 0
            this.isDot = false
            if (this.displayedValue !== 0) {
                if (type === 'sqrt') {
                    this.currentValue = Math.sqrt(this.displayedValue)
                } else if (type === '1/x') {
                    this.currentValue = 1 / this.displayedValue
                } else if (type === '+/-') {
                    this.currentValue = -this.displayedValue
                } else if (type === '%') {
                    this.currentValue = this.displayedValue / 100
                }
            }            
            this.$emit('display', this.displayedValue)
        },
        dot () {
            this.isDot = true
            // this.decimalDigit += 1
        },
        memoryStore (type) {
            this.decimalDigit = 0
            this.isDot = false
            if (type === 'MC') {
                if (this.isMemoried !== '') {                
                    this.memoryValue = 0
                    this.isMemoried = ''
                }
            }
            else if (type === 'MS') {
                if (this.displayedValue !== 0) {
                    this.memoryValue = this.displayedValue
                    this.isMemoried = 'M'
                }
            }
            else if (type === 'MR') {
                if (this.memoryValue !== 0) {
                    this.currentValue = this.memoryValue
                }
            }
            else if (type === 'M+') {
                if (this.displayedValue !== 0) {
                    this.memoryValue += this.displayedValue
                }
            }
            this.$emit('display', this.displayedValue)
        }
    },
    computed: {
        displayedValue () {
            return this.savedValue ? this.currentValue ? this.currentValue : this.savedValue : this.currentValue
        }
    },
    watch: {
        isShow (val) {
            if (val) {                
                window.addEventListener('click', this.checkClickOn)
                this.$el.classList.add('show')
                this.currentValue = this.value                
                this.clickCount = 0                
            } else {               
                this.close()              
            }            
        }        
    }
}
</script>
