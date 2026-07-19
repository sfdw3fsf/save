'use client';
import LineInformation from '@/components/common/line-information/LineInformation';
import '@/components/reservation/reservation.css';
import { branchKeys, getBranchListApi } from '@/store/branch/branch.api';
import { branchRespToDto } from '@/store/branch/branch.service';
import { deleteReservationDetailApi, getReservationDetailApi, reservationKey } from '@/store/reservation/reservation.api';
import { reservationRespToDto } from '@/store/reservation/reservation.service';
import { useReservationStore } from '@/store/reservation/reservation.store';
import { formatKoreanDate } from '@/utils/constant/utils/date.utils';
import { getVisitorLabelByValue } from '@/utils/helper/reservation.helper';
import { orString } from '@/utils/parser/io.parser';
import { keepPreviousData, useMutation, useQuery } from '@tanstack/react-query';
import { useRouter } from 'next/navigation';
import { Suspense, useEffect, useMemo } from 'react';

const ConfirmReservationPage = () => {
  const router = useRouter();
  const { selectedBranch, reservationId, setReservationId } = useReservationStore((state) => state);

  const { data: reservationDetailData } = useQuery({
    queryKey: reservationKey.detail(reservationId ?? 0),
    queryFn: () => getReservationDetailApi(reservationId ?? 0),
    select: (response) => {
      return response?.data.data && reservationRespToDto(response?.data.data);
    },
    placeholderData: keepPreviousData,
  });

  const { data: branchListData } = useQuery({
    queryKey: branchKeys.lists(),
    queryFn: () => getBranchListApi(),
    select: (response) => {
      return response?.data && response?.data.data.map(branchRespToDto);
    },
    placeholderData: keepPreviousData,
  });

  const activeBranchIndex = useMemo(() => {
    return branchListData?.findIndex((item) => item.id === selectedBranch?.id);
  }, [selectedBranch, branchListData]);

  const { mutateAsync: mutateDeleteReservation } = useMutation({
    mutationFn: (id: number) => deleteReservationDetailApi(id),
  });

  const DATA = useMemo(() => {
    return [
      {
        label: '예약 일시',
        value: `${formatKoreanDate(reservationDetailData?.date ?? 0)}, ${orString(reservationDetailData?.time)}`,
      },
      {
        label: '아이 정보',
        value: `${orString(reservationDetailData?.petName)}, ${orString(reservationDetailData?.petWeight)}, ${orString(reservationDetailData?.petType)}`,
      },
      {
        label: '보호자 정보',
        value: `${orString(reservationDetailData?.guardianName)}, ${orString(reservationDetailData?.guardianPhone)}`,
      },
      { label: '방문 인원', value: orString(getVisitorLabelByValue(reservationDetailData?.numberOfVisitor)) },
    ];
  }, [reservationDetailData]);

  const handleNavigate = () => {
    const branchId = activeBranchIndex !== undefined ? `branch-${activeBranchIndex + 1}` : null;
    if (branchId) {
      router.push(`/branch/${branchId}`);
    }
  };

  const handleNavigateMainPage = () => {
    router.push(`/`);
  };

  const handleCancle = () => {
    const confirmed = window.confirm('취소 후 되돌릴 수 없습니다.\n정말 취소하시겠습니까?');
    if (confirmed) {
      mutateDeleteReservation(reservationId ?? 0);
      alert('취소되었습니다.');
      setReservationId(undefined);
    } else {
      // User clicked "Cancel"
    }
  };

  useEffect(() => {
    if (!reservationId) router.push('/');
  }, [reservationId]);

  console.log('aaa', selectedBranch, branchListData);

  const imgBranch = activeBranchIndex === 0 ? '/images/branch/branch-1/1.jpg' : '/images/branch/branch-2/1.jfif';

  return (
    <div className='cont !mb-[89px] md:!mb-[136px]' id='reserve'>
      <div className='!mt-[30px] cont-head cont-head2 flex justify-center items-center flex-col text-center md:!mb-[26px] md:!mt-[80px] '>
        <h1 className='text-green-main md:text-[30px] text-[25px] font-bold leading-none'>예약 정보 확인</h1>
        <p className='mt-[17px] text-grey-sub text-[15px] md:text-[17px]'>예약이 성공적으로 완료 되었습니다.</p>
      </div>
      <div className='lmt maxw '>
        <div className='sec-body'>
          <div className='mx-auto  md:max-w-[469px]'>
            <div className='px-[5px]'>
              <img alt='branch' src={imgBranch} className='rounded-t-[10px]'></img>
              <div className='border-[1.5px] flex flex-col justify-center min-h-[333px] border-grey-neutral rounded-b-[10px]'>
                <div className='inline-flex flex-col px-[24px] pb-[35px] pt-[49px] md:!py-[40px] md:px-[98px]'>
                  <div className='flex justify-between'>
                    <div className='text-green-main font-bold text-[18px] leading-[23px] md:text-[18px]'>
                      {`펫포레스트 ${orString(reservationDetailData?.branchName)}`}
                    </div>
                    <button
                      onClick={handleNavigate}
                      className='text-grey-dark w-[42px] h-[23px] border border-grey-cloud rounded-[3px] text-[11px] md:w-[60px] md:h-[23px]'>
                      <div className='opacity-50'>더보기</div>
                    </button>
                  </div>
                  <div className='border opacity-50 border-grey-neutral my-[12px] md:my-[12px] '></div>
                  <div className='flex gap-[22px] md:gap-[20px]'>
                    <LineInformation data={DATA} />
                  </div>
                </div>
                <div className='px-[24px] mb-[30px] flex gap-[10px] text-grey-dark text font-bold text-[16px] leading-[14px] md:gap-[17px] md:px-[31px]'>
                  <button
                    onClick={() => handleCancle()}
                    className='rounded-[3px] flex-1 h-[38px] bg-grey-ghost md:h-[48px] text-[14px] font-bold  md:text-[16px]'>
                    예약 취소
                  </button>
                  <button
                    onClick={() => handleNavigateMainPage()}
                    className='rounded-[3px] flex-1 h-[38px] bg-green-light md:h-[48px] text-[14px] font-bold md:text-[16px]'>
                    메인화면으로
                  </button>
                </div>
              </div>
            </div>
            <div className='font-medium text-[13px] leading-[24px] text-grey-slate pt-[18px] md:pt-[26px]'>
              <div>· 예약 상세 정보는 고객님이 입력하신 핸드폰 번호 알림톡으로 전송 되었습니다.</div>
              <div>· 예약 정보를 확인 하시고 예약 일시에 펫포레스트로 방문 하시기를 바랍니다.</div>
              <div>· 예약 취소 및 예약 변경을 원하시는 경우 먼저 예약 취소를 하신 후 다시 예약 신청을 해주시면 됩니다.</div>
              <div>· 궁금한 사항은 고객센터(1577-0996)으로 문의 바랍니다.</div>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
};
const WrappedConfirmReservationPagee = () => (
  <Suspense>
    <ConfirmReservationPage />
  </Suspense>
);
export default WrappedConfirmReservationPagee;
