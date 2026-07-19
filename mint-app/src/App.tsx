import '@mantine/core/styles.css';
import '@mantine/dates/styles.css';
import dayjs from 'dayjs';
import { useEffect } from 'react';
import 'react-big-calendar/lib/addons/dragAndDrop/styles.css';
import 'react-big-calendar/lib/css/react-big-calendar.css';
import 'tippy.js/dist/tippy.css';
import i18n from './i18n';
import './index.css';
import AppRouter from './router/AppRouter';

const App = () => {
  const language = i18n.language;
  useEffect(() => {
    dayjs.locale(language);
  }, [language]);

  return (
    <div className='min-h-screen w-full bg-slate-50 text-slate-800'>
      <AppRouter />
    </div>
  );
};

export default App;
