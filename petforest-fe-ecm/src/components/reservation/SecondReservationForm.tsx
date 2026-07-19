import { ReservationDTO } from '@/store/reservation/dto/reservation.dto';
import { createReservationApi } from '@/store/reservation/reservation.api';
import { reservationDtoToReq } from '@/store/reservation/reservation.service';
import { useReservationStore } from '@/store/reservation/reservation.store';
import { MAPPING_WEEK_DAYS } from '@/utils/constant/reservation/mapping.constant';
import { RESERVATION_PRECAUTIONS } from '@/utils/constant/reservation/reservation-info.constant';
import { getWeekday } from '@/utils/constant/utils/date.utils';
import { getErrorMessages } from '@/utils/helper/reservation.helper';
import { parseTimestampValue } from '@/utils/parser/date-time.parser';
import { reservationSchema } from '@/utils/schema/reservation.schema';
import { CheckIcon, ChevronRightIcon } from '@chakra-ui/icons';
import { Modal, ModalBody, ModalContent, ModalHeader, ModalOverlay } from '@chakra-ui/react';
import { yupResolver } from '@hookform/resolvers/yup';
import { useMutation } from '@tanstack/react-query';
import { Button } from 'antd';
import { useRouter } from 'next/navigation';
import { useMemo, useState } from 'react';
import { FormProvider, useForm } from 'react-hook-form';
import PrivacyPolicyPage from './PrivacyPolicyPage';
import { ReservationGuardianInfo } from './ReservationGuardianInfo';
import { ReservationPetInfo } from './ReservationPetInfo';
import { ReservationServiceInfo } from './ReservationServiceInfo';
import TermOfUsePage from './TermOfUsePage';

type SecondReservationFormProps = {
  date: number;
  time: string;
};

export const SecondReservationForm = ({ date, time }: SecondReservationFormProps) => {
  const router = useRouter();
  const { selectedBranch, setReservationId, setSelectedBranch } = useReservationStore((state) => state);

  const methods = useForm({
    resolver: yupResolver(reservationSchema),
    defaultValues: {
      reserveInfo: {
        branch: selectedBranch?.id,
        date: date,
        time: time,
      },
      petInfo: {
        petName: '',
        petWeight: undefined,
        petClassification: 0,
      },
      guardianInfo: { guardianName: '', phoneNumber: '', message: '' },
      additionalInfo: {
        isNotSellected: false,
        flowerBouquetService: false,
        funeralService: { isActive: false, accompanny: undefined, address: '', detailedAddress: '' },
        lucetteService: false,
      },
    },
    mode: 'onSubmit',
    shouldFocusError: false,
  });

  const { handleSubmit } = methods;
  const {
    formState: { errors },
  } = methods;

  const [agreeTermCondition, setAgreeTermCondition] = useState({ termOfUse: false, privacyPolicy: false });
  const [isOpenTermOfUse, setIsOpenTermOfUse] = useState<boolean>(false);
  const [isOpenPrivacyPolicy, setIsOpenPrivacyPolicy] = useState<boolean>(false);
  const [isOpenError, setIsOpenError] = useState<boolean>(false);

  const canSubmitReservation = useMemo(() => {
    if (agreeTermCondition.termOfUse === true && agreeTermCondition.privacyPolicy === true) return true;
    return false;
  }, [agreeTermCondition]);

  const { mutate: mutateCreateReservation, isPending } = useMutation({
    mutationFn: (body: ReservationDTO) => createReservationApi(reservationDtoToReq(body)),
    onSuccess: (response) => {
      handleNavigateConfirm(response?.data.data.id || 0);
    },
  });

  const handleCheckAll = () => {
    if (canSubmitReservation) {
      setAgreeTermCondition({ termOfUse: false, privacyPolicy: false });
      return;
    }
    setAgreeTermCondition({ termOfUse: true, privacyPolicy: true });
  };

  const handleChangeAgreeTermCondition = (field: 'termOfUse' | 'privacyPolicy') => {
    setAgreeTermCondition((prevState) => ({
      ...prevState,
      [field]: !prevState[field],
    }));
  };

  const onSubmit = (data: ReservationDTO) => {
    mutateCreateReservation(data);
  };

  const handleNavigateConfirm = (id: number) => {
    setReservationId(id);
    router.push(`/reservation/confirm`);
  };
  const onError = () => {
    setIsOpenError(true);
  };

  return (
    <div className='cont-body ' id='form'>
      <div className='mt-[44px] md:mt-[41px] relative'>
        <div className='selected_summary md:!w-full'>
          <div className=' flex justify-center items-center w-full py-[22px] gap-[22px] md:gap-[40px] md:py-[17px]'>
            <div className='inline-flex items-center gap-2.5'>
              <span className='text-[15px] font-bold text-grey-main md:text-[16px]'>
                <img src={'/icon/icon-location.svg'} alt='icon-location' />
              </span>
              <span className='text-[15px] font-bold text-grey-blue md:text-[16px]'>{selectedBranch?.name}</span>
            </div>
            <div className='inline-flex items-center gap-2.5'>
              <span className='text-[15px] font-bold text-grey-main md:text-[16px]'>
                <img src={'/icon/icon-calendar.svg'} alt='icon-location' />
              </span>
              <span className='text-[15px] font-bold text-grey-blue md:text-[16px]'>{`${parseTimestampValue(date)}(${MAPPING_WEEK_DAYS[getWeekday(parseTimestampValue(date))].short}) ${time}`}</span>
            </div>
          </div>
        </div>
        <div className='w-full mt-[49px] border border-lightest-grey py-[21px] px-[28px] rounded-lg text-left mx-auto md:py-[33px] md:mt-[42px]'>
          <div className='text-black font-semibold mb-[3px] text:text-[14.5px] md:text-[17px] md:mb-[11px]'>
            간편예약시 주의사항
          </div>
          {RESERVATION_PRECAUTIONS.map((text) => (
            <div key={text} className='font-normal text-[14px]'>
              {text}
            </div>
          ))}
        </div>
        <FormProvider {...methods}>
          <form onSubmit={handleSubmit(onSubmit, onError)}>
            <ReservationPetInfo />
            <div className='hr'></div>
            <ReservationGuardianInfo />
            <div className='hr'></div>
            <ReservationServiceInfo />
            <div className='hr'></div>
            <div className='mt-[30px] text-left'>
              <div>
                <button
                  className='h-[54px] bg-grey-silver flex items-center w-full rounded-[5px]'
                  type='button'
                  onClick={() => handleCheckAll()}>
                  <div
                    className={`rounded-[6px] ml-[26px] mr-[14px] h-[26px] w-[26px] border border-lightest-grey flex items-center justify-center ${canSubmitReservation ? 'bg-green-main' : 'bg-white'}`}>
                    {canSubmitReservation && <CheckIcon color='white'></CheckIcon>}
                  </div>
                  <div className={`text-[16px] font-semibold text-grey-dark`}>전체 약관에 동의 합니다.</div>
                </button>
                <div className='relative flex items-center w-full mt-[10px] md:mt-[16x]'>
                  <button className='flex items-center' type='button' onClick={() => handleChangeAgreeTermCondition('termOfUse')}>
                    <div
                      className={`rounded-[6px] ml-[26px] mr-[14px] h-[26px] w-[26px] border border-lightest-grey flex items-center justify-center ${agreeTermCondition.termOfUse === true ? 'bg-green-main' : 'bg-white'}`}>
                      {agreeTermCondition.termOfUse === true && <CheckIcon color='white'></CheckIcon>}
                    </div>
                  </button>
                  <div className={`text-[14px] font-medium text-grey-text`}>이용 약관 동의 (필수)</div>
                  <ChevronRightIcon
                    className='text-esset text-grey-text cursor-pointer'
                    onClick={() => setIsOpenTermOfUse(true)}
                  />
                </div>
                <div className='relative flex items-center w-full mt-[15px] '>
                  <button
                    className='flex items-center'
                    type='button'
                    onClick={() => handleChangeAgreeTermCondition('privacyPolicy')}>
                    <div
                      className={`rounded-[6px] ml-[26px] mr-[14px] h-[26px] w-[26px] border border-lightest-grey flex items-center justify-center ${agreeTermCondition.privacyPolicy === true ? 'bg-green-main' : 'bg-white'}`}>
                      {agreeTermCondition.privacyPolicy === true && <CheckIcon color='white'></CheckIcon>}
                    </div>
                  </button>
                  <div className={`text-[14px] text-grey-text`}>개인정보 취급방침 동의 (필수)</div>
                  <ChevronRightIcon
                    className='text-esset text-grey-text cursor-pointer'
                    onClick={() => setIsOpenPrivacyPolicy(true)}
                  />
                </div>
              </div>
            </div>
            <div className='mt-[45px]'>
              <Button
                loading={isPending}
                disabled={!canSubmitReservation}
                htmlType='submit'
                className={`btn-choose-branch !text-grey-main !font-extrabold !border-0 md:!h-[52px] ${agreeTermCondition.termOfUse === true && agreeTermCondition.privacyPolicy === true && 'active'}`}>
                펫포레스트 장례예약
              </Button>
            </div>
          </form>
        </FormProvider>
      </div>
      <Modal isOpen={isOpenError} onClose={() => setIsOpenError(false)} isCentered>
        <ModalOverlay />
        <ModalContent className=' mx-[25px] md:mx-0'>
          <ModalHeader className='text-center !text-[16px]'>{getErrorMessages(errors)}</ModalHeader>
          <ModalBody className='text-center text-[14px] text-grey-main'>
            <button onClick={() => setIsOpenError(false)}>확인</button>
          </ModalBody>
        </ModalContent>
      </Modal>
      <Modal size={'full'} isOpen={isOpenPrivacyPolicy} onClose={() => setIsOpenPrivacyPolicy(false)} isCentered>
        <ModalOverlay />
        <ModalContent className='!mt-auto mx-[25px] md:mx-0'>
          <ModalBody>
            <PrivacyPolicyPage onClose={() => setIsOpenPrivacyPolicy(false)} />
          </ModalBody>
        </ModalContent>
      </Modal>
      <Modal size={'full'} isOpen={isOpenTermOfUse} onClose={() => setIsOpenTermOfUse(false)} isCentered>
        <ModalOverlay />
        <ModalContent className='!mt-auto mx-[25px] md:mx-0'>
          <TermOfUsePage onClose={() => setIsOpenTermOfUse(false)} />
        </ModalContent>
      </Modal>
    </div>
  );
};
