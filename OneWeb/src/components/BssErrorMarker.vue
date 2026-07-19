<template>
<div :class="{error: hasError}" style="border: 1px solid transparent; border-radius: 5px">
    <slot></slot>
</div>
</template>
<style scoped>
    .error {
        border-color: #dc3545 !important;
    }
</style>
<script>
export default {
    name: 'bss-error-marker',
    props: {
        hasError: Boolean
    }
}
</script>
