import { ROUTES } from '@/router/routes.constant';
import { useAuthStore } from '@/store/auth.store';
import { USER_ROLE } from '@/utils/enums/user/user-role.enum';
import { IMAGES } from '@/utils/theme';
import { useTranslation } from 'react-i18next';
import { Link } from 'react-router-dom';

const InquiryRequestButton = () => {
  const { t: tCommon } = useTranslation('common');

  const { userInfo } = useAuthStore();

  if (!userInfo || userInfo.userRole === USER_ROLE.MEMBER) {
    return (
      <Link
        to={ROUTES.PROJECT_REQUEST.INDIVIDUAL.REQUEST}
        className='fixed bg-[#34987B] bottom-10 right-10 z-[14] rounded-lg shadow-lg py-2'>
        <img src={IMAGES.APP.FLOATING_BUTTON} className='w-12 m-auto pl-2' />
        <p className=' text-white px-3 max-w-28 text-center'>{tCommon('home.sticky_button')}</p>
      </Link>
    );
  }

  return <></>;
};

export default InquiryRequestButton;
