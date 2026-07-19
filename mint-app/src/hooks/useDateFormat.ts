import i18n from '@/i18n';
import dayjs from 'dayjs';
const useDateFormat = (format: 'DD/MM/YYYY' | 'MMMM D, YYYY' | 'MMMM D, YYYY h:mm A', date?: Date | string) => {
  const language = i18n.language;
  if (!date) return '';
  if (language === 'ko') return dayjs(date).format('YYYY년 MM월 DD일');
  return dayjs(date).locale(language).format(format);
};

export default useDateFormat;
