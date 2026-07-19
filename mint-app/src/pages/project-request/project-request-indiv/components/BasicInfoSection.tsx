import FileCard from '@/components/core/file-card/FileCard';
import Spinner from '@/components/core/spinner/Spinner';
import { userDetailsAPI } from '@/data/user/user.api';
import i18n, { I18nLanguage } from '@/i18n';
import { Attachment } from '@/types/attachment.type';
import { QUERY_KEYS } from '@/utils/constants/data.constant';
import { formatKoreanPhoneNumber } from '@/utils/helpers/form.helper';
import { withFallbackStr } from '@/utils/helpers/string.helper';
import { useQuery } from '@tanstack/react-query';
import { useMemo } from 'react';
import { useTranslation } from 'react-i18next';
import { selectedUserBusinessFieldCategories } from '../helpers/user-business-field.helpers';
import InfoCard from './InfoCard';
import InfoCardItem from './InfoCardItem';

const BasicInfoSection = (): JSX.Element => {
  const { t: tCommon } = useTranslation('common');
  const { data: userDetails, isFetching: isFetchingUserDetails } = useQuery({
    queryKey: [QUERY_KEYS.USER_DETAILS],
    queryFn: userDetailsAPI,
  });

  const currentCode = i18n.language as I18nLanguage;

  const userInfo = useMemo(() => userDetails?.data, [userDetails?.data]);

  const businessFields = useMemo(() => {
    if (userInfo?.businessFieldCategories) {
      const { businessFieldsInKorean, businessFieldsInEnglish } = selectedUserBusinessFieldCategories(
        userInfo?.businessFieldCategories,
      );
      if (currentCode === 'ko') return businessFieldsInKorean;
      else return businessFieldsInEnglish;
    }
    return '_';
  }, [currentCode, userInfo?.businessFieldCategories]);

  if (isFetchingUserDetails) {
    return <Spinner raw className='size-10 self-center my-10' />;
  }

  return (
    <>
      <InfoCard
        className='gap-4 pb-4 max-md:gap-3 max-md:pb-3'
        childrenCont='gap-y-5 gap-x-3 max-md:gap-3 my-0 max-md:px-3'
        headerClassName='py-3 max-md:px-3'
        title={tCommon('project_request.contact_information')}>
        <InfoCardItem className='my-0' label={tCommon('project_request.email')} content={withFallbackStr(userInfo?.email)} />
        <InfoCardItem className='my-0' label={tCommon('project_request.id')} content={withFallbackStr(userInfo?.username)} />
        <InfoCardItem
          className='my-0'
          label={tCommon('project_request.person_in_charge')}
          content={withFallbackStr(userInfo?.fullName)}
        />
        <InfoCardItem
          className='my-0'
          label={tCommon('project_request.phone_number')}
          content={formatKoreanPhoneNumber(userInfo?.phoneNumber)}
        />
      </InfoCard>
      <InfoCard
        className='gap-4 pb-4 max-md:gap-3 max-md:pb-3'
        childrenCont='gap-y-5 gap-x-3 max-md:gap-3 my-0 max-md:px-3'
        headerClassName='py-3 max-md:px-3'
        title={tCommon('project_request.corporate_information')}>
        <InfoCardItem
          className='my-0'
          label={tCommon('project_request.company_name')}
          content={withFallbackStr(userInfo?.companyName)}
        />
        <InfoCardItem
          className='my-0'
          label={tCommon('project_request.person_in_charge')}
          content={withFallbackStr(userInfo?.fullName)}
        />
        <InfoCardItem
          className='my-0'
          label={tCommon('project_request.position')}
          content={withFallbackStr(userInfo?.personInCharge)}
        />
        <InfoCardItem
          className='my-0'
          label={tCommon('project_request.contact_email')}
          content={withFallbackStr(userInfo?.internalEmail)}
        />
        <InfoCardItem className='my-0' label={tCommon('project_request.business_area')} content={businessFields} />
        <InfoCardItem
          className='my-0'
          label={tCommon('project_request.person_in_charge_department_name')}
          content={withFallbackStr(userInfo?.departmentName)}
        />
        {!!userInfo?.memberAttachments && (
          <InfoCardItem
            label={tCommon('project_request.attach_file')}
            className='md:col-span-2 col-span-1 my-0'
            content={
              <div className='grid grid-cols-1 md:grid-cols-2 gap-2 mt-2'>
                {userInfo?.memberAttachments?.map((attachment: Attachment, index: number) => {
                  return (
                    <button
                      key={index}
                      className='col-span-1'
                      form='_noexist'
                      onClick={() => {
                        window.open(attachment.url, '_blank');
                      }}>
                      <FileCard key={index} file={attachment as any} />
                    </button>
                  );
                })}
              </div>
            }
          />
        )}
      </InfoCard>
    </>
  );
};

export default BasicInfoSection;
