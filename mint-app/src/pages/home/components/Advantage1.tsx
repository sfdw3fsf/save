import Background from '@/assets/images/background-image-2.svg';
import ModalDialog from '@/components/core/modal-dialog/ModalDialog';
import Spinner from '@/components/core/spinner/Spinner';
import TextInput from '@/components/core/text-input/TextInput';
import ArrowLeftIcon from '@/components/icons/ArrowLeftIcon';
import ArrowRightIcon from '@/components/icons/ArrowRightIcon';
import InfoIcon from '@/components/icons/InfoIcon';
import PlusIcon from '@/components/icons/PlusIcon';
import XIcon from '@/components/icons/XIcon';
import { getExpertsByMedicalField, getMedicalFieldOverview } from '@/data/home/home-api';
import { ExpertMedicalRESP } from '@/data/home/response/expert-medical.response';
import { ICONS } from '@/utils/theme';
import { useQuery } from '@tanstack/react-query';
import { startTransition, useEffect, useRef, useState } from 'react';
import { useTranslation } from 'react-i18next';
import Slider from 'react-slick';
import Tag from './Tag';
import TeamMemberCard from './TeamMemberCard';

import 'slick-carousel/slick/slick-theme.css';
import 'slick-carousel/slick/slick.css';
import i18next from 'i18next';
import { ExpertRESP } from '@/data/project/response/project.response';

const settings = {
  speed: 500,
  slidesToShow: 1,
  slidesToScroll: 2,
  infinite: false,
  variableWidth: true,
  responsive: [
    {
      breakpoint: 1024,
      settings: {
        slidesToShow: 3,
        slidesToScroll: 2,
      },
    },
    {
      breakpoint: 768,
      settings: {
        slidesToShow: 2,
        slidesToScroll: 2,
      },
    },
    {
      breakpoint: 640,
      settings: {
        slidesToShow: 1,
        slidesToScroll: 1,
      },
    },
  ],
};

const Advantage1 = () => {
  const { t: tCommon } = useTranslation('common');
  const [openDialogModal, setOpenDialogModal] = useState(false);

  const isEnglish = i18next.language === 'en';

  const { data } = useQuery({
    queryKey: ['getExpertByMedicalField'],
    queryFn: () => getMedicalFieldOverview(),
  });

  const [medicalFields, setMedicalField] = useState<ExpertMedicalRESP[]>();
  const [selectedMedicalField, setSelectedMedicalField] = useState<ExpertMedicalRESP[]>();
  const [filteredMedicalFields, setFilteredMedicalFields] = useState<ExpertMedicalRESP[]>();
  const [selectedFilteredMedicalField, setSelectedFilteredMedicalField] = useState<ExpertMedicalRESP[]>([]);
  const slider = useRef<Slider | null>(null);

  useEffect(() => {
    // sort the medical fields
    if (data?.data) return;

    if (isEnglish) data?.data?.sort((a, b) => a.categoryFieldNameInEnglish.localeCompare(b.categoryFieldNameInEnglish));
    else data?.data?.sort((a, b) => a.categoryFieldName.localeCompare(b.categoryFieldName));

    setSelectedMedicalField(data?.data ? [data.data[0]] : []);
    setFilteredMedicalFields(data?.data);
    setMedicalField(data?.data);
  }, [data, isEnglish]);

  const { data: expertByMedicalField, isFetching } = useQuery({
    queryKey: ['getExpertByMedicalField', selectedMedicalField],
    queryFn: () =>
      getExpertsByMedicalField({
        medicalFieldCategoryIds: selectedMedicalField?.map((m) => m.categoryFieldId) || [],
        size: 1000,
        page: 1,
      }),
    enabled: selectedMedicalField && selectedMedicalField?.length > 0,
  });

  const maskMiddleFive = (str: string) => {
    const len = str.length;
    if (len <= 2) return str;
    if (len == 3) return str[0] + '*' + str[2];

    const half = Math.floor(len / 2);
    const start = Math.floor(half / 2);
    const end = Math.ceil(len - start);

    const mask = '*'.repeat(end - start);

    return str.substring(0, start) + mask + str.substring(end);
  };

  const handleMedicalFieldSearch = (value: string) => {
    if (medicalFields != null)
      setFilteredMedicalFields(
        medicalFields.filter((item) =>
          isEnglish
            ? item.categoryFieldNameInEnglish.toLocaleLowerCase().includes(value.toLowerCase())
            : item.categoryFieldName.toLowerCase().includes(value.toLowerCase()),
        ),
      );
  };

  // handle select medical field in the modal dialog (open when click See more button)
  const handleSelectMedicalField = (item: ExpertMedicalRESP) => {
    const isAlreadySelected = selectedFilteredMedicalField.some((selected) => selected.categoryFieldId === item.categoryFieldId);

    const updatedSelectedFilteredMedicalField = isAlreadySelected
      ? selectedFilteredMedicalField.filter((selected) => selected.categoryFieldId !== item.categoryFieldId)
      : [...selectedFilteredMedicalField, item];

    setSelectedFilteredMedicalField(updatedSelectedFilteredMedicalField);
    if (updatedSelectedFilteredMedicalField.length > 0) setSelectedMedicalField(updatedSelectedFilteredMedicalField);
    // Reorder the medical fields based on the updated selectedFilteredMedicalField
    setMedicalField((prev) => {
      if (!prev) return prev;

      const selected = prev.filter((field) =>
        updatedSelectedFilteredMedicalField.some((selected) => selected.categoryFieldId === field.categoryFieldId),
      );

      // back to default
      if (selected.length === 0) return [...(data?.data || [])];

      const unselected = prev.filter(
        (field) => !updatedSelectedFilteredMedicalField.some((selected) => selected.categoryFieldId === field.categoryFieldId),
      );

      return [...selected, ...unselected];
    });
  };

  const buildExpertExperience = (yearOfExperience: number) => {
    if (isEnglish) {
      return `${yearOfExperience} years of clinical experience`;
    } else return '임상경력 ' + yearOfExperience.toString() + '년';
  };

  const buildDepartmentName = (expert: ExpertRESP) => {
    const expertMedicalField = selectedMedicalField?.find((m) => m.categoryFieldName === expert.department);

    if (isEnglish) return expertMedicalField?.categoryFieldNameInEnglish || expert.department;
    else return expertMedicalField?.categoryFieldName || expert.department;
  };

  useEffect(() => {
    if (!selectedMedicalField) return;
    const allSelectedIds = new Set(selectedMedicalField?.map((m) => m.categoryFieldId));
    const notSelected = medicalFields?.filter((m) => !allSelectedIds.has(m.categoryFieldId));
    setMedicalField([...(selectedMedicalField || []), ...(notSelected || [])]);
  }, [selectedMedicalField]);

  return (
    <div className='relative w-full h-fit py-24'>
      <img src={Background} className='object-contain w-96 absolute -right-0' />
      <div className='xl:w-4/5 w-full mx-auto lg:px-28 px-3'>
        <p className='text-primary-600 text-sm font-medium text-center'>{tCommon('home.Dr_wiz_expert')}</p>
        <p className='text-slate-800 md:text-4xl text-3xl font-bold mt-4  text-center mb-16'>
          {tCommon('home.Dr_wiz_expert_title1')}
        </p>
        <div className='border bg-slate-50 rounded-xl'>
          <div className='flex items-center gap-2 py-3 px-3 border-b-2 border-slate-200'>
            <div className='bg-[#EE6A5F] w-3 h-3 rounded-full border border-[#CE5347]' />
            <div className='bg-[#F5BD4F] w-3 h-3 rounded-full border border-[#D6A243]' />
            <div className='bg-[#61C454] w-3 h-3 rounded-full border border-[#58A942]' />
          </div>
          <div className='pt-6 px-8 pb-4'>
            <div className='flex items-center gap-2 mb-4'>
              <InfoIcon className='text-slate-500' />
              <p className='text-slate-500 text-base'>{tCommon('home.department_description')}</p>
            </div>
            <div className='flex items-center justify-between'>
              <div className='flex items-center gap-2 overflow-x-auto static-scrollbar'>
                {medicalFields?.map((item) => (
                  <Tag
                    isActive={selectedMedicalField?.some((m) => m.categoryFieldId == item.categoryFieldId)}
                    badgeLabel={item.totalExpert}
                    label={isEnglish ? item.categoryFieldNameInEnglish : item.categoryFieldName}
                    key={item.categoryFieldId}
                    onClick={() =>
                      setSelectedMedicalField((prev) => {
                        if (!prev) return [item];
                        const exists = prev?.some((m) => m.categoryFieldId === item.categoryFieldId);

                        if (exists) {
                          if (prev.length === 1) return prev;
                          return prev?.filter((m) => m.categoryFieldId !== item.categoryFieldId);
                        } else return [...(prev || []), item];
                      })
                    }
                  />
                ))}
              </div>
              <button
                className='flex items-center gap-2 hover:bg-primary-100 px-3 py-2 rounded-md w-fit'
                onClick={() => {
                  startTransition(() => {
                    setSelectedFilteredMedicalField(selectedMedicalField || []);
                    setFilteredMedicalFields(medicalFields);
                    setOpenDialogModal(true);
                  });
                }}>
                <p className='text-primary-600 font-medium text-nowrap'>{tCommon('home.see_more')}</p>
                <PlusIcon className='text-primary-600' />
              </button>
            </div>
          </div>
          <div className='px-8 pt-4 pb-6 h-96 overflow-hidden '>
            <div className='flex items-center justify-between mb-6 h-fit'>
              <p className='text-slate-800 font-medium text-lg'>
                {isEnglish
                  ? selectedMedicalField?.map(
                      (s, i) => s.categoryFieldNameInEnglish + (i == selectedMedicalField.length - 1 ? '' : ', '),
                    )
                  : selectedMedicalField?.map((s, i) => s.categoryFieldName + (i == selectedMedicalField.length - 1 ? '' : ', '))}
              </p>
              <div className='gap-4 flex'>
                <button
                  className='p-[10px] rounded-full border border-slate-200 bg-white'
                  onClick={() => slider.current?.slickPrev()}>
                  <ArrowLeftIcon />
                </button>
                <button
                  className='p-[10px] rounded-full border border-slate-200 bg-white'
                  onClick={() => slider.current?.slickNext()}>
                  <ArrowRightIcon />
                </button>
              </div>
            </div>
            {isFetching ? (
              <div className='flex items-center justify-center mt-10'>
                <Spinner raw />
              </div>
            ) : (
              <Slider ref={slider} {...settings}>
                {expertByMedicalField?.data.map((expert) => (
                  <TeamMemberCard
                    key={expert.expertId}
                    imgSrc={expert?.avatar?.url || ICONS.FILE.DEFAULT_AVATAR}
                    name={`${expert?.fullName.charAt(0)}${'*'.repeat(expert?.fullName.length - 1)}`}
                    department={buildDepartmentName(expert)}
                    // TODO: corr hospital
                    hospital={maskMiddleFive(expert.detailedField)}
                    experience={buildExpertExperience(expert.yearOfExperience)}
                  />
                ))}
              </Slider>
            )}
          </div>
        </div>
      </div>
      <ModalDialog visible={openDialogModal} onClose={setOpenDialogModal}>
        <div className='p-2 flex-col gap-2 flex lg:max-w-[600px] md:max-w-96 max-w-72 md:min-w-96'>
          <div className='flex items-center justify-between '>
            <p className='text-slate-800 text-lg'>{tCommon('home.category_list')}</p>
            <button
              onClick={() => {
                setOpenDialogModal(false);
                setFilteredMedicalFields(medicalFields);
              }}>
              <XIcon />
            </button>
          </div>
          <div className='flex flex-col gap-3'>
            <TextInput
              placeholder={tCommon('home.search_category_list')}
              onChangeText={(value) => handleMedicalFieldSearch(value)}
            />
            <div className='flex items-center flex-wrap gap-3'>
              {filteredMedicalFields && filteredMedicalFields.length > 0 ? (
                filteredMedicalFields?.map((item) => (
                  <Tag
                    badgeLabel={item.totalExpert}
                    label={isEnglish ? item.categoryFieldNameInEnglish : item.categoryFieldName}
                    key={item.categoryFieldId}
                    onClick={() => handleSelectMedicalField(item)}
                    isActive={selectedFilteredMedicalField.some((selected) => selected.categoryFieldId === item.categoryFieldId)}
                  />
                ))
              ) : (
                <p className='m-auto'>{tCommon('home.no_result')}</p>
              )}
            </div>
          </div>
        </div>
      </ModalDialog>
    </div>
  );
};

export default Advantage1;
