import Button from '@/components/core/button/Button';
import Divider from '@/components/core/divider/Divider';
import { SuccessToast } from '@/components/core/success-notification/SuccessNotification';
import { selectExpertAPI } from '@/data/project/project-indiv.api';
import { ExpertRESP } from '@/data/project/response/project.response';
import i18n from '@/i18n';
import InfoCard from '@/pages/project-request/project-request-indiv/components/InfoCard';
import InfoCardItem from '@/pages/project-request/project-request-indiv/components/InfoCardItem';
import { combineBusinessFields } from '@/pages/project-request/project-request-indiv/helpers/project-request-div.helpers';
import { ROUTES } from '@/router/routes.constant';
import { QUERY_KEYS } from '@/utils/constants/data.constant';
import { onError } from '@/utils/helpers/data.helper';
import { getNameByLanguage } from '@/utils/helpers/form.helper';
import { withFallbackStr } from '@/utils/helpers/string.helper';
import { useMutation, useQueryClient } from '@tanstack/react-query';
import { useCallback, useMemo } from 'react';
import { useTranslation } from 'react-i18next';
import { useNavigate } from 'react-router-dom';
import { twMerge } from 'tailwind-merge';
import AvatarWithLabel from '../components/AvatarWithLabel';

type ExpertProfileProps = {
  expert: ExpertRESP;
  onCancel: () => void;
  projectId: number;
  showBottomButton?: boolean;
};
const ExpertProfile = ({ expert, projectId, onCancel, showBottomButton = true }: ExpertProfileProps): JSX.Element => {
  const language = i18n.language;
  const { t: tCommon } = useTranslation('common');
  const queryClient = useQueryClient();
  const formattedYear = useCallback((year: number, month: number) => `${year}.${String(month).padStart(2, '0')}`, []);
  const businessField = useMemo(() => combineBusinessFields(expert?.businessFieldCategories), [expert?.businessFieldCategories]);
  const navigate = useNavigate();

  // APIS
  const { mutate: selectExpertAPi, isPending } = useMutation({
    mutationFn: () => selectExpertAPI(projectId, { expertId: expert?.expertId }),
    onSuccess: () => {
      navigate(`${ROUTES.PROJECT_MANAGEMENT.INDIVIDUAL}/${projectId}`);
      queryClient.invalidateQueries({ queryKey: [QUERY_KEYS.PROJECTS.INDIVIDUAL.LIST, projectId] });
      SuccessToast({
        label: tCommon('expert_matching.select_success'),
        content: tCommon('expert_matching.select_success_content'),
      });
    },
    onError,
  });

  // METHODS
  const handleSelectExpert = () => {
    selectExpertAPi();
  };
  return (
    <div className='flex flex-col w-full h-[calc(100vh-84px)]'>
      <div className='flex flex-col gap-4 px-6 py-4 overflow-y-auto h-full static-scrollbar'>
        <AvatarWithLabel size='lg' url={expert?.avatar?.url} />
        <div className='grid grid-cols-2 gap-0 my-2'>
          <InfoCardItem label={tCommon('expert_matching.name')} content={expert?.fullName} className='col-span-1 gap-0' />
          <InfoCardItem
            label={tCommon('expert_matching.affiliation')}
            content={withFallbackStr(expert?.affiliation) as string}
            className='col-span-1'
          />
          <InfoCardItem
            label={tCommon('expert_matching.personal_experience')}
            content={`${withFallbackStr(expert?.yearOfExperience.toString())} ${tCommon('expert_matching.years')}` as string}
            className='col-span-1'
          />
          <InfoCardItem
            label={tCommon('expert_matching.department')}
            content={withFallbackStr(expert?.department) as string}
            className='col-span-1'
          />
          <InfoCardItem
            label={tCommon('expert_matching.detailed_field')}
            content={withFallbackStr(expert?.detailedField) as string}
            className='col-span-2'
          />
          <InfoCardItem
            label={tCommon('expert_matching.main_business_area')}
            className='col-span-2'
            content={
              <div>
                {businessField?.map(({ label, allFields }, index) => (
                  <div key={index} className='flex flex-col w-full gap-1'>
                    <div className='text-comp-text-tertiary text-xs font-normal'>{getNameByLanguage(language, label)}</div>
                    <div className='font-medium text-comp-text-default text-sm'>{getNameByLanguage(language, allFields)}</div>
                  </div>
                ))}
              </div>
            }
          />
        </div>
        {expert?.educations?.length !== 0 && (
          <InfoCard title={tCommon('expert_matching.education')} childrenCont='md:grid-cols-1 grid-cols-1'>
            {expert?.educations?.map((education, index) => {
              return (
                <InfoCardItem
                  key={index}
                  label={`${formattedYear(education.entranceYear, education.entranceMonth)} - ${formattedYear(education.graduationYear, education.graduationMonth)}`}
                  content={
                    <div className='flex flex-col gap-1'>
                      <div className='font-bold text-slate-800 text-md'>{education.name}</div>
                      <div className='font-medium text-slate-500 text-sm'>{education.detail}</div>
                    </div>
                  }
                />
              );
            })}
          </InfoCard>
        )}

        {expert?.experiences?.length !== 0 && (
          <InfoCard title={tCommon('expert_matching.experience')}>
            {expert?.experiences?.map((experience, index) => {
              return (
                <InfoCardItem
                  key={index}
                  label={
                    experience.endYear === new Date().getFullYear() && experience.endMonth === new Date().getMonth() + 1
                      ? `${experience.startYear} - ${tCommon('my-page.present')}`
                      : `${experience.startYear} - ${experience.endYear}`
                  }
                  content={
                    <div className='flex flex-col gap-1'>
                      <div className='font-bold text-slate-800 text-md'>{experience.name}</div>
                      <div className='font-medium text-slate-500 text-sm'>{experience.detail}</div>
                    </div>
                  }
                />
              );
            })}
          </InfoCard>
        )}
      </div>
      {showBottomButton && (
        <div className={'flex gap-2 items-center px-6 pb-6 mt-auto flex-col w-full'}>
          <Divider />
          <div className={twMerge('flex gap-3 justify-between w-full py-2 ', language === 'en' && 'flex-wrap sm:flex-nowrap')}>
            <Button className='w-full' onClick={onCancel}>
              {tCommon('expert_matching.see_other_expert')}
            </Button>
            <Button className='w-full' variant='primary' onClick={handleSelectExpert} isLoading={isPending}>
              {tCommon('expert_matching.choose_this_expert')}
            </Button>
          </div>
        </div>
      )}
    </div>
  );
};

export default ExpertProfile;
