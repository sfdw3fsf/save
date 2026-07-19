import Content7 from '@/assets/images/Content_7.png';
import { ROUTES } from '@/router/routes.constant';
import { useAuthStore } from '@/store/auth.store';
import { USER_ROLE } from '@/utils/enums/user/user-role.enum';
import { useHover } from '@uidotdev/usehooks';
import { t } from 'i18next';
import { useMemo } from 'react';
import { useTranslation } from 'react-i18next';
import { FiArrowRight } from 'react-icons/fi';
import { Link } from 'react-router-dom';
import { twMerge } from 'tailwind-merge';

type ProcessItemProps = {
  index: number;
  title: string;
  description: string;
  button?: {
    label: string;
    action: any;
    link: string;
  };
};

const ProcessItem = ({ index, title, description, button }: ProcessItemProps) => {
  const { accessToken, userInfo } = useAuthStore();
  const isLoggedIn = useMemo<boolean>(() => Boolean(accessToken && userInfo), [accessToken, userInfo]);
  const linkProjectManagement = useMemo(() => {
    switch (userInfo?.userRole) {
      case USER_ROLE.MEMBER:
        return ROUTES.PROJECT_MANAGEMENT.INDIVIDUAL;
      case USER_ROLE.EXPERT:
        return ROUTES.PROJECT_MANAGEMENT.EXPERT;
      default:
        return ROUTES.PROJECT_MANAGEMENT.INDIVIDUAL;
    }
  }, [userInfo?.userRole]);
  const [ref, hovering] = useHover();

  return index === 4 ? (
    <Link to={isLoggedIn ? linkProjectManagement : ROUTES.LOGIN}>
      <div
        ref={ref}
        className={twMerge(
          'border-l-4 border-slate-200 p-4 w-full transition-all',
          hovering && 'rounded-2xl shadow-lg shad shadow-[#bbecfa] drop-shadow-md border-primary-400',
        )}>
        <p className='text-slate-800 text-xl font-bold mb-2 whitespace-pre-line'>
          {index}. {title}
        </p>
        <p className='text-slate-500 text-base font-normal whitespace-pre-line'>{description}</p>

        {hovering && button && (
          <button className={twMerge('flex items-center gap-2 mt-3')}>
            <p className='text-primary-400 font-semibold text-base'>{button.label}</p>
            <FiArrowRight className='text-primary-400' size={20} />
          </button>
        )}
      </div>
    </Link>
  ) : (
    <div
      ref={ref}
      className={twMerge(
        'border-l-4 border-slate-200 p-4 w-full transition-all',
        hovering && 'rounded-2xl shadow-lg shad shadow-[#bbecfa] drop-shadow-md border-primary-400',
      )}>
      <p className='text-slate-800 text-xl font-bold mb-2 whitespace-pre-line'>
        {index}. {title}
      </p>
      <p className='text-slate-500 text-base font-normal whitespace-pre-line'>{description}</p>

      {hovering && button && (
        <Link to={button.link} className={twMerge('flex items-center gap-2 mt-3')}>
          <p className='text-primary-400 font-semibold text-base'>{button.label}</p>
          <FiArrowRight className='text-primary-400' size={20} />
        </Link>
      )}
    </div>
  );
};
const Process = () => {
  const { t: tCommon } = useTranslation('common');

  const DATA = [
    {
      index: 1,
      title: tCommon('home.process1_title'),
      description: tCommon('home.process1_description'),
    },
    {
      index: 2,
      title: tCommon('home.process2_title'),
      description: tCommon('home.process2_description'),
      button: {
        label: t('common:home.process_contact'),
        action: t('common:home.process_contact'),
        link: ROUTES.PROJECT_REQUEST.INDIVIDUAL.REQUEST,
      },
    },
    {
      index: 3,
      title: tCommon('home.process3_title'),
      description: tCommon('home.process3_description'),
    },
    {
      index: 4,
      title: tCommon('home.process4_title'),
      description: tCommon('home.process4_description'),
    },
    {
      index: 5,
      title: tCommon('home.process5_title'),
      description: tCommon('home.process5_description'),
    },
  ];

  return (
    <div className='lg:px-28 px-3 lg:py-24 py-12 xl:w-4/5 w-full'>
      <h2 className='text-primary-600 text-sm text-center mb-4 font-medium'>Process</h2>
      <p className='text-slate-800 md:text-4xl text-3xl text-center font-bold md:px-0 px-5 mx-auto mb-16'>
        {tCommon('home.process_title')}
      </p>
      <div className='flex md:flex-row flex-col justify-between gap-10 w-full mx-auto'>
        <div className='flex flex-col md:w-2/4 w-full transition-all'>
          {DATA.map((item, index) => (
            <ProcessItem index={item.index} title={item.title} description={item.description} key={index} button={item.button} />
          ))}
        </div>
        <img src={Content7} className='md:w-2/4 w-full max-h-[560px] object-fill' />
      </div>
    </div>
  );
};

export default Process;
