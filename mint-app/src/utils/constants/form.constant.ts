import { TFunction } from 'i18next';

export const PATTERNS: Record<string, RegExp> = {
  EMAIL: /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/,
  PASSWORD: /^(?=.*[A-Za-z])(?=.*\d)(?=.*[@#$!%*?&])[A-Za-z\d@$!%*?&]{8,12}$/,
  OTP: /^\d{6}$/,
};

export const PASSWORD_RULES = (value: string, tCommon: TFunction<'common'>) => [
  { text: tCommon('change_password.characters'), isValid: /^(?=.*[A-Za-z]).{8,12}$/.test(value) },
  { text: tCommon('change_password.a_number'), isValid: /\d/.test(value) },
  { text: tCommon('change_password.a_special_character'), isValid: /[@#$!%*?&]/.test(value) },
];
