import Button from '@/components/core/button/Button';
import Card from '@/components/core/card/Card';
import Divider from '@/components/core/divider/Divider';
import { ROUTES } from '@/router/routes.constant';
import { ReactNode } from 'react';
import { useTranslation } from 'react-i18next';
import { useNavigate } from 'react-router-dom';
import FindTitle, { FindTitleProps } from './FindTitle';

// export const PARAM_KEY = 'method';
// const PARAM_VALUE_PHONE_NUMBER = 'phone-number';
// export const PARAM_VALUE_EMAIL = 'email';

type FindContainerProps = {
  className?: string;
  children?: ReactNode;
  form: {
    id: string;
    submitLabel: string;
    onSubmit: () => void;
    isLoading?: boolean;
  };
  title: FindTitleProps;
};

const FindContainer = ({ className, children, form, title }: FindContainerProps): JSX.Element => {
  const { t: tCommon } = useTranslation('common');

  // const location = useLocation();
  const navigate = useNavigate();

  // const params = queryString.parse(location.search);
  // const paramValue = useMemo(() => params[PARAM_KEY], [params]);

  // const items = useMemo<ViewToggleItemProps[]>(() => {
  //   return [
  //     {
  //       label: tCommon('find_id_password.by_phone_number'),
  //       onClick: () => handleNavigate(PARAM_VALUE_PHONE_NUMBER),
  //       isActive: paramValue !== PARAM_VALUE_EMAIL,
  //     },
  //     {
  //       label: tCommon('find_id_password.by_email'),
  //       onClick: () => handleNavigate(PARAM_VALUE_EMAIL),
  //       isActive: paramValue === PARAM_VALUE_EMAIL,
  //     },
  //   ];
  // }, [paramValue, tCommon]);

  // const handleNavigate = (to: string) => {
  //   navigate({ search: queryString.stringify({ ...params, [PARAM_KEY]: to }) });
  // };

  return (
    <Card className={className}>
      <div className='flex w-full flex-col gap-6'>
        {/* <ViewToggle items={items} /> */}

        <FindTitle {...title} />

        <Divider />

        <form
          id={form.id}
          className='flex w-full flex-col gap-3 text-left'
          onSubmit={(e) => {
            e.preventDefault();
            form.onSubmit();
          }}>
          {children}
        </form>
      </div>

      <div className='flex w-full flex-col items-center gap-6'>
        <Button form={form.id} variant='primary' className='w-full' isLoading={form.isLoading}>
          {form.submitLabel}
        </Button>

        <div className='flex items-center justify-center gap-1 text-center'>
          <span className='text-sm font-normal text-slate-500'>{tCommon('find_id_password.do_you_have_an_account')}</span>
          <Button className='min-h-5' sizing='sm' variant='linkColor' onClick={() => navigate(ROUTES.LOGIN)}>
            {tCommon('find_id_password.log_in')}
          </Button>
        </div>
      </div>
    </Card>
  );
};

export default FindContainer;
